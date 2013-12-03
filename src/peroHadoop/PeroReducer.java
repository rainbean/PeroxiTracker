package peroHadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

public class PeroReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	private Logger log = Logger.getLogger(PeroReducer.class);

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		// Expected input key   : file path
		//          input value : # of files, collected from individual text files. 
		int sum = 0;
		for (IntWritable i : values) {
			sum += i.get();
		}
		log.info("Mapper key: " + key + ", value: " + sum);
		context.write(key, new IntWritable(sum));
	}
}
