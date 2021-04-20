package kuliah.kmers.kmercount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class KmerCount {

	public static void main(String[] args) throws Exception {
		System.out.println("generating file ");
		int numFiles = 10000;
		int lengthSequence = 1000;
		String input_dir = args[0]+"/input_"+numFiles+"_"+lengthSequence+"";

		File f = new File(input_dir);
		f.mkdir();
		SequenceGenerator.makeGeneratedFiles(input_dir,lengthSequence,numFiles);
		System.out.println("Configuring Job MapReduce ");
		//BasicConfigurator.configure();
		Configuration conf = new Configuration();
		conf.set("k", "3");
		System.out.println("processing...");
		// get the start time
		long start = System.nanoTime();
		Job job = new Job(conf);
		job.setJarByClass(KmerCount.class);
		FileInputFormat.addInputPath(job, new Path(input_dir));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String output_dir = args[1]+"/"+ dtf.format(now);
		System.out.println("output_dir : " + output_dir);
		FileOutputFormat.setOutputPath(job, new Path(output_dir));
		job.setMapperClass(KmerCountMapper.class);
		job.setReducerClass(KmerCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		boolean wait= job.waitForCompletion(true);
		// get the end time
		long end = System.nanoTime();
		// execution time
		long execution = end - start;
		System.out.println("Execution time: " + execution + " nanoseconds");
		System.exit(wait ? 0 : 1);
	}

}
