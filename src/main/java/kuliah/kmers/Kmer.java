/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuliah.kmers;

/**
 *
 * @author koharudin
 */
public abstract class Kmer implements SlidingEvent{
   public void slidingWindow(String sequence, int k){
       for (int i = 0; i < sequence.length(); i++) {
            try {
                String sub = sequence.substring(i, i + k);
                this.onSliding(sub);
            } catch (Exception e) {

            }
        }
   } 
   
   
}
