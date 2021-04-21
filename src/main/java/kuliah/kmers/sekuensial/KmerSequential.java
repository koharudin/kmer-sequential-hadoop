package kuliah.kmers.sekuensial;

import java.io.File;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author koharudin
 */
public class KmerSequential {

    public static HashMap<String, Long>   result;

    public KmerSequential() {

    }
    public static void print(){
        Iterator<String> itr = KmerSequential.result.keySet().iterator();
        while (itr.hasNext())
        {
            String key = itr.next();
            Long value = KmerSequential.result.get(key);

            System.out.println(key + "=" + value);
        }
    }
    public static void main(String[] args) {
        KmerSequential.result = new HashMap<String, Long>();
        int numFiles = 1000;
        int lengthSequence = 10000;
        String input_dir = args[0]+"/input_"+numFiles+"_"+lengthSequence+"";
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
                            Long n = KmerSequential.result.get(s);
                            KmerSequential.result.put(s, (n == null) ? 1 : n + 1);
                        }
                    }
                }
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
        KmerSequential.print();
    }



}
