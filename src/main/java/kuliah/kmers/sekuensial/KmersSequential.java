package kuliah.kmers.sekuensial;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import kuliah.kmers.RandomGeneratorSequence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author koharudin
 */
public class KmersSequential {
    HashMap result;

    public KmersSequential() {
        this.result = new HashMap<String, Integer>();
    }
    public void slidingSeq(String sequence, Integer k) {
            for (int i = 0; i < sequence.length(); i++) {
                try {
                    String sub = sequence.substring(i, i + k);
                    if(result.containsKey(sub)){
                        Integer sum = (Integer) result.get(sub);
                        result.put(sub, sum+1);
                    }
                    else {
                         result.put(sub, 1);
                    }
                    
                } catch (Exception e) {

                }
            }
        }
    public static void main(String [] args){
        String sequences = RandomGeneratorSequence.generate(100000);
        System.out.println(sequences);
        long startTime = System.currentTimeMillis();

        KmersSequential ks = new KmersSequential();
        ks.slidingSeq(sequences, 2);
        long stopTime = System.currentTimeMillis();
        System.out.println((stopTime - startTime));
        
        for (Iterator iterator = ks.result.keySet().iterator(); iterator.hasNext();) {
            Object next = iterator.next();
            System.out.println(next+">"+ks.result.get(next));
        }
        System.out.println("123");
    }
}
