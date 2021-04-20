package kuliah.kmers.sekuensial;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author koharudin
 */
public class KmerCovidSequential {

    public static HashMap<String, HashMap> result;
    public static HashMap<String, Integer> perm_all;

    public KmerCovidSequential() {

    }
    public static void print(){
        Iterator<String> itr = KmerCovidSequential.result.keySet().iterator();
        while (itr.hasNext())
        {
            String key = itr.next();
            HashMap value = KmerCovidSequential.result.get(key);

            System.out.println(key + "=" + value);
        }
    }
    public static void main(String[] args) {
        KmerCovidSequential.result = new HashMap<String, HashMap>();
        perm_all = new HashMap<String, Integer>();
        String arr[] = { "A","C","T","G","N"};
        int len = arr.length;
        int L = 3;

        // function call
        print(arr, len, L);

        String input_dir = args[0];
        // Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        System.out.println(input_dir);
        File f = new File(input_dir);
        // Populates the array with names of files and directories
        pathnames = f.list();
        // For each pathname in the pathnames array
        int index=1;
        int k=3;
        // get the start time
        long start = System.nanoTime();
        for (String pathname : pathnames) {
            HashMap<String,Integer> curr = (HashMap<String,Integer>)perm_all.clone();
            // Print the names of files and directories
            System.out.println("index "+index+" > "+pathname);
            String fullname = input_dir+"/"+pathname;
            File myObj = new File(fullname);
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                    if (data.matches("[ACTGN]+")) {
                        for (int i = 0; i <= data.length() - k; i++) {
                            String s = data.substring(i, i + k);
                            Integer n = curr.get(s);
                            curr.put(s, (n == null) ? 1 : n + 1);
                        }
                    }
                }
                result.put(pathname,curr);
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
            index++;
        }
        // get the end time
        long end = System.nanoTime();
        // execution time
        long execution = end - start;
        System.out.println("Execution time: " + execution + " nanoseconds");
        KmerCovidSequential.print();

        KmerCovidSequential r = new KmerCovidSequential();
        writeCsv("/Users/koharudin/output.csv",result);
    }
    public static void writeCsv(String fileCsv,HashMap<String,HashMap> result){

        try (FileWriter csvWriter = new FileWriter(fileCsv)) {
            csvWriter.append("Object");
            Iterator<String> itr2 = perm_all.keySet().iterator();
            while (itr2.hasNext())
            {
                csvWriter.append(",");
                csvWriter.append(itr2.next());
            }
            csvWriter.append("\n");
            Iterator<String> itr = result.keySet().iterator();
            while (itr.hasNext())
            {
                String key = itr.next();
                csvWriter.append(key);
                HashMap<String,Integer> vals =  result.get(key);
                for (String s : vals.keySet()) {
                    csvWriter.append(",");
                    csvWriter.append(vals.get(s).toString());
                }
                csvWriter.append("\n");
            }
        }
        catch (Exception e){

        }
    }
    // Convert the number to Lth
// base and print the sequence
    static void convert_To_Len_th_base(int n, String arr[],
                                       int len, int L)
    {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < L; i++)
        {
            // Print the ith element
            // of sequence
            System.out.print(arr[n % len]);
            str.append(arr[n % len]);
            n /= len;
        }
        perm_all.put(str.toString(),0);
    }

    // Print all the permuataions
    static void print(String arr[], int len, int L)
    {
        // There can be (len)^l
        // permutations
        for (int i = 0;
             i < (int)Math.pow(len, L); i++)
        {
            // Convert i to len th base
            convert_To_Len_th_base(i, arr, len, L);
        }
    }


}
