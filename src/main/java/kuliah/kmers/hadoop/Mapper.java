/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuliah.kmers.hadoop;

import java.io.IOException;
import kuliah.kmers.SlidingEvent;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/**
 *
 * @author koharudin
 */
public class Mapper extends org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context
    ) throws IOException, InterruptedException {
        System.out.println("text line =>"+value.toString());
        if (value.toString().startsWith(">")) {
            return;
        }
        this.slidingWindow(value.toString(), context);
    }

    public void slidingWindow(String sequence, org.apache.hadoop.mapreduce.Mapper.Context context) {
        System.out.println("seliding..."+sequence);
        Configuration conf = context.getConfiguration();
        Integer k = Integer.parseInt(conf.get("k"));
        for (int i = 0; i < sequence.length(); i++) {
            try {
                String sub = sequence.substring(i, i + k);
                word.set(sub);
                context.write(word, one);
            } catch (Exception e) {

            }
        }
    }
}
