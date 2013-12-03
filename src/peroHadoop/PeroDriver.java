package peroHadoop;

import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PeroDriver extends Configured implements Tool {
	
	private void dumpArgs(String[] args)  {
		for (int i=0; i<args.length; i++) {
			System.out.println(args[i]);
		}
	}
	
	private void dumpConf() {
		Configuration cf = getConf();
		
		Iterator<Map.Entry<String, String>> iter = cf.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> i = iter.next();
			System.out.println(i.getKey() + ": " + i.getValue());
		}
	}

	//public static void main(String[] args) throws Exception {
	public int run(String[] args) throws Exception {
		// Debug
		// dumpArgs();
		
		/*
		 * Validate that two arguments were passed from the command line.
		 */
		if (args.length != 2) {
			System.out.printf("Usage: PeroDriver <input dir> <output dir>\n");
			System.exit(-1);
		}

		/*
		 * Instantiate a Job object for your job's configuration. 
		 */
		//Job job = new Job();
		//Job job = Job.getInstance();
		Job job = Job.getInstance(getConf()); // inherit configuration from ToolRunner

		/*
		 * Specify the jar file that contains your driver, mapper, and reducer.
		 * Hadoop will transfer this jar file to nodes in your cluster running 
		 * mapper and reducer tasks.
		 */
		job.setJarByClass(PeroDriver.class);
		job.setMapperClass(PeroMapper.class);
		job.setReducerClass(PeroReducer.class);

		/*
		 * Specify an easily-decipherable name for the job.
		 * This job name will appear in reports and logs.
		 */
		job.setJobName("Peroxi_Tracker");
		
		// Debug dump configuration 
		// dumpConf()
		// System.exit(0);
		

		/*
		 * Specify working environment
		 */
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);


		// remove output folder
		FileSystem.get(job.getConfiguration()).delete(out, true);
		//FileSystem.getLocal(job.getConfiguration()).delete(out, true); // not works in HDFS

		//job.setInputFormatClass(TextInputFormat.class);
		//job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		/*
		 * Start the MapReduce job and wait for it to finish.
		 * If it finishes successfully, return 0. If not, return 1.
		 */
		boolean success = job.waitForCompletion(true);
		//System.exit(success ? 0 : 1);
		return (success ? 0 : 1);
	}

	public static void main(String[] args)throws Exception {
		// leverages the ToolRunner class to parse arguments, particular for -libjars to load external libraries
		int error = ToolRunner.run(new PeroDriver(), args); // 
		System.exit(error);
	}
}

