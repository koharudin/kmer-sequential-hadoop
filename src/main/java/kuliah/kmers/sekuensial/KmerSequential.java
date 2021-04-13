package kuliah.kmers.sekuensial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import kuliah.kmers.Kmer;
import kuliah.kmers.RandomGeneratorSequence;
import kuliah.kmers.SlidingEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author koharudin
 */
public class KmerSequential extends Kmer {

    HashMap result;

    public KmerSequential() {
        this.result = new HashMap<String, Integer>();
    }
    public static void main(String[] args) {
        String sequences = RandomGeneratorSequence.generate(10);
        System.out.println(sequences);
        long startTime = System.currentTimeMillis();

        KmerSequential ks = new KmerSequential();
        ks.slidingWindow(sequences, 2);
        long stopTime = System.currentTimeMillis();
        System.out.println((stopTime - startTime));

        for (Iterator iterator = ks.result.keySet().iterator(); iterator.hasNext();) {
            Object next = iterator.next();
            System.out.println(next + ">" + ks.result.get(next));
        }
        System.out.println("123");
    }

    @Override
    public void onSliding(String key) {
        if (result.containsKey(key)) {
            Integer sum = (Integer) result.get(key);
            result.put(key, sum + 1);
        } else {
            result.put(key, 1);
        }
    }

}
