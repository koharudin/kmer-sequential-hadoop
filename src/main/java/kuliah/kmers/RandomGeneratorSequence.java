/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuliah.kmers;

import java.util.Random;

/**
 *
 * @author koharudin
 */
public class RandomGeneratorSequence {
    public static String generate(int n){
        String seq = "ACTG";
        String generatedSequence =  "";
        Random r = new Random(123L);
   
        for(int i=1;i<=n;i++){
            generatedSequence+=seq.charAt(r.nextInt(4));
        }
        return generatedSequence;
    }
}
