package peroJava;

import ij.IJ;
import ij.ImagePlus;
import java.io.File;

public class Channel {
private float[] features;
	
	public Channel(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		for (int fileInList = 0; fileInList < files.length; fileInList++) {
			if (files[fileInList].isFile() == true && !files[fileInList].getName().startsWith(".")) {
				
				String name = files[fileInList].getName();
				
				ImagePlus imp = IJ.openImage(new File(path, name).toString());
				Well well1 = new Well(imp);
				well1.contrastEnhance();
				well1.meanShift(3, 25);
				//well1.showImage();
				well1.saveAs(path, "/enhanced", name);
			}
		}
		
	}
	
	public void getAllFeatures() {
		
	}
	
	public void getPositiveFeatures() {
		
	}

	public void getNegativeFeatures() {
	
	}
	
	private void normalize(){
		//return
	}
	
}
