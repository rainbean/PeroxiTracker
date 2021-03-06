package peroJava;

import ij.*;
import ij.process.*;
import ij.io.*;

public class Well {
	
	
	private ImagePlus imp;
	
	public Well(ImagePlus imp) {
		
		ImageProcessor ip = imp.getProcessor().convertToFloat();
		this.imp = imp;
		this.imp.setProcessor(ip);
	}
	
	public void showImage() {
		imp.show();
	}
	
	public void contrastEnhance() {
		// IJ.run() requires X11 environment, not feasible for Azure
		
		IJ.run(imp, "Enhance Contrast", "saturated=0.35 normalize");
		imp.updateImage();
	}
	
	public void meanShift(int spacial, float color) {
		
		Mean_Shift ms = new Mean_Shift();
		ImageProcessor ip = this.imp.getProcessor();
		
		ms.isRGB = this.imp!=null && this.imp.getType()==ImagePlus.COLOR_RGB;
		ms.rad = spacial;
		ms.rad2 = spacial*spacial;
		ms.radCol = color;
		ms.radCol2 = color*color;
		
		ms.run(ip);
		this.imp.setProcessor(ip);
	}
	
	public void topHat() {	
	}
	
	public void histogram(){
		
	}
	
	public void saveAs(String path, String subdir, String name){
		FileSaver fs = new FileSaver(this.imp);
		fs.saveAsPng(path + subdir + name);
	}
}