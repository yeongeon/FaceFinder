package io.mulberry.facefinder.thread;

import io.mulberry.facefinder.opencv.OpenCVFaceDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileGenThread extends Thread{
  private static Logger LOG = LoggerFactory.getLogger(FileGenThread.class);

	final static Class<FileGenThread> CLASS_LOADER = FileGenThread.class;

	private BufferedImage bimg = null;
	private int hashcode = 0;
	private int loop = 0;
	
	OpenCVFaceDetector<FileGenThread> faceDetector = new OpenCVFaceDetector<FileGenThread>(CLASS_LOADER);
	
	public FileGenThread(BufferedImage bimg, int hashcode, int loop, int rotate, boolean isProcessing){
		if(isProcessing){
			this.bimg = faceDetector.faceDetect(bimg);
		} else {
			this.bimg = bimg;
		}
		this.hashcode = hashcode;
		this.loop = loop;
		
		if(rotate>0){
			this.loop = this.loop%rotate;
		}
	}

	@Override
	public void run() {
		String path = CLASS_LOADER.getResource("/output/frames").getPath();
		File target = new File(path);
		if(!target.exists() && !target.isDirectory()){
			if(target.mkdir()){
        LOG.info("Created path: %s", path);
			}
		}
		String filename = String.format("%s/cam_saved_%d_%d.png", path, this.hashcode, this.loop);
		File outputfile = new File(filename);
		try {
			ImageIO.write(bimg, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}