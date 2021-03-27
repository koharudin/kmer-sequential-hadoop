/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuliah.kmers.hadoop;

import java.io.IOException;
import java.time.LocalTime;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Kmers {

    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            if (value.toString().startsWith(">")) {
                return;
            }
           // System.out.println("Fasta " + value.toString());
            this.slidingSeq(value.toString(), context);

        }

        public void slidingSeq(String sequence, Context context) {
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

    public static class IntSumReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                Context context
        ) throws IOException, InterruptedException {
           // Thread.sleep(1000);
          //  System.out.println("Reduceeee "+key);
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("mycounter....");
        long startTime = System.currentTimeMillis();
        Configuration conf = new Configuration();
       // conf.set("mapred.max.split.size", "26843550");
       // conf.set("mapred.compress.map.output", "true");
        //conf.set("mapred.output.compress", "true");
       // conf.set("mapred.reduce.tasks", "125");
        conf.set("k", args[0]);
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(Kmers.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[1]));
        String output_dir = args[2]+"/"+(LocalTime.now());
        FileOutputFormat.setOutputPath(job, new Path(output_dir));
        long stopTime = System.currentTimeMillis();
        System.out.println("Time is "+(stopTime - startTime));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
