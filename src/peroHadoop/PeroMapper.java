package peroHadoop;

import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import peroJava.Well;

public class PeroMapper extends Mapper<Object, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	private Logger log = Logger.getLogger(PeroMapper.class);

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		// Expected input key   : read bytes
		//          input value : filepath, collected from individual text files. 
		log.info("Mapper key: " + key + ", value: " + value);
		
		// Get file system prefix
		// Either "file:///" for Eclipse or "hdfs://localhost.localdomain:8020"
		// context.getConfiguration().get("fs.defaultFS");

		// Check whether file exist
		Path path = new Path(value.toString());
		FileSystem fs = path.getFileSystem(context.getConfiguration());
		if (!fs.exists(path)) {
			log.warn("Invalid input path: " + value);
		} else {
			process(fs, path);
		}
		
		// Word count example 
		/*
		String line = value.toString();
		StringTokenizer tok = new StringTokenizer(line);
		while (tok.hasMoreTokens()) {
			word.set(tok.nextToken());
			context.write(word, one);
		}
		*/
	}

	// Parameters for Mean-Shift --
	private final static int sr = 3;
	private final static int cr = 25;

	private void process(FileSystem fs, Path path) throws IOException {
		System.out.println("Processing " + path.toString() + " ...");

		String name = path.getName();
		
		ImagePlus imp;
		// open file through local file path
		// imp = IJ.openImage(path.toString());
		//imp = new Opener().openURL(path.toString());
		imp = new Opener().openTiff(fs.open(path), name);
		
		Well well1 = new Well(imp);
		well1.contrastEnhance();

		//Uncomment for executing Mean-Shift --
		well1.meanShift(sr, cr);

		//well1.showImage();
		well1.saveAs(path.getParent().toString(), "/enhanced", name);
	}
}
