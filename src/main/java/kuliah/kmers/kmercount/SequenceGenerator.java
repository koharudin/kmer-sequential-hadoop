package kuliah.kmers.kmercount;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SequenceGenerator {
    public static String generate(int length){
        String optional="ACTGN";
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=length;i++){
            int r = new Random().nextInt(4);
            sb.append(optional.charAt(r));
        }
        return sb.toString();
    }
    public static void makeGeneratedFiles(String input_dir,int lengthSequence, int numFiles){
        for (int i=1;i<=numFiles;i++) {
            try (FileWriter fileWriter = new FileWriter(input_dir + "/input"+i+".txt")) {
                String generatedString = generate(lengthSequence);
                fileWriter.write(generatedString);
                fileWriter.close();
            }catch (IOException e) {
                // Cxception handling
            }
        }
    }
    public static void main(String[] args){
        makeGeneratedFiles(args[0],Integer.valueOf(args[1]),Integer.valueOf(args[2]));
    }
}
