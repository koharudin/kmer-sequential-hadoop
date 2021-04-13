package kuliah.kmers.kmercount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.time.LocalTime;

public class KmerCount {

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		System.out.println("kmer custom...");
		//conf.set("fs.defaultFS", "hdfs://192.168.0.202:9000");
		//conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

		conf.set("mapred.max.split.size", "26843550");
		conf.set("mapred.compress.map.output", "true");
		//conf.set("mapred.output.compress", "true");
		//conf.set("mapred.reduce.tasks", "125");

		//conf.set("mapreduce.jobtracker.address", "192.168.0.202:9000");
		///conf.set("mapreduce.framework.name", "yarn");
		//conf.set("yarn.resourcemanager.address", "127.0.0.1:8032");
		conf.set("k", "5");
		
		Job job = new Job(conf);
		job.setJarByClass(KmerCount.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		String output_dir = args[1]+"/"+("oke104");
		System.out.println("output_dir : " + output_dir);
		FileOutputFormat.setOutputPath(job, new Path(output_dir));
		job.setMapperClass(KmerCountMapper.class);
		job.setReducerClass(KmerCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
