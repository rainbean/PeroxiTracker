package peroJava;

import ij.*;
import java.io.*;
import javax.swing.*;

public class Test {
	// Parameters for Mean-Shift --
	private int sr = 3;
	private int cr = 25;
	private String path;
	
	private void process(File file) {
		System.out.println("Processing " + file.getName() + " ...");
		
		String name = file.getName();
		
		ImagePlus imp = IJ.openImage(file.getPath());
		Well well1 = new Well(imp);
		well1.contrastEnhance();

//Uncomment for executing Mean-Shift --
		well1.meanShift(sr, cr);
		
		//well1.showImage();
		well1.saveAs(file.getParent(), "/enhanced", name);
	}
	
	public Test(String path) {
		this.path = path;
	}
	
	public void run() {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int fileInList = 0; fileInList < files.length; fileInList++) {
				if (files[fileInList].isFile() == true && !files[fileInList].getName().startsWith(".")) {
					process(files[fileInList]);
				}
			}
		} else {
			process(file);
		}
		System.out.println("Completed.");
	}
	
	public static void main(String[] args) {
		String path;
		if (args.length == 0) {		
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.showOpenDialog(fc);
			path = fc.getSelectedFile().toString();
		} else {
			path = args[0];
			File f = new File(path);
			if (!f.exists()) {
				System.out.println("Invalid input path.");
				System.exit(1);
			}
		}

		new Test(path).run();
	}
}