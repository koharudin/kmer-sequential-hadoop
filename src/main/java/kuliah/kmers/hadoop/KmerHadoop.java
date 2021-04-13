/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuliah.kmers.hadoop;

import java.io.IOException;
import java.time.LocalTime;
import java.util.StringTokenizer;
import kuliah.kmers.Kmer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KmerHadoop{


    public static void main(String[] args) throws Exception {
        System.out.println("mycounter....");
        long startTime = System.currentTimeMillis();
        Configuration conf = new Configuration();
       // conf.set("mapred.max.split.size", "26843550");
       // conf.set("mapred.compress.map.output", "true");
        //conf.set("mapred.output.compress", "true");
       // conf.set("mapred.reduce.tasks", "125");
        conf.set("k","5");
        Job job = Job.getInstance(conf, "kmer");
        job.setJarByClass(KmerHadoop.class);
        job.setMapperClass(Mapper.class);
        job.setCombinerClass(Reducer.class);
        job.setReducerClass(Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.out.println("args[0] "+args[0]);
        System.out.println("args[1] "+args[1]);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        String output_dir = args[1]+"/"+(LocalTime.now());
        FileOutputFormat.setOutputPath(job, new Path(output_dir));
        long stopTime = System.currentTimeMillis();
        System.out.println("Time is "+(stopTime - startTime));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
