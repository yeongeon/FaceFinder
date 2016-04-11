package io.mulberry.facefinder.thread;

import com.github.sarxos.webcam.Webcam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class CamImageThread extends Thread{
  private static Logger LOG = LoggerFactory.getLogger(CamImageThread.class);

	private Webcam webcam = null;
	private Canvas canvas = null;
	private int loop = 0;
	private boolean isFileGen = false;
	private boolean isFileFaceDetect = false;

	public CamImageThread(Webcam webcam, Canvas canvas){
		this(webcam, canvas, false);
	}
	public CamImageThread(Webcam webcam, Canvas canvas, boolean isFileGen){
		this(webcam, canvas, isFileGen, false);
	}
	public CamImageThread(Webcam webcam, Canvas canvas, boolean isFileGen, boolean isFileFaceDetect){
		this.webcam = webcam; 
		this.canvas = canvas;
		this.isFileGen = isFileGen;
		this.isFileFaceDetect = isFileFaceDetect;
	}
	

	@Override
	public void run() {
		
		while(this.webcam!=null && this.webcam.isOpen()){
			BufferedImage bimg = this.webcam.getImage();
						
			Graphics2D g = bimg.createGraphics();
			g.drawImage(bimg, 0, 0, -bimg.getWidth(), bimg.getHeight(), this.canvas);
			//g.drawImage(bimg, 0, 0, this.canvas);
			//this.canvas.update(g);;
			this.canvas.repaint();
			
			if(this.isFileGen){
				new FileGenThread(bimg, this.webcam.hashCode(), loop, 0, this.isFileFaceDetect).start();
			}
			
			loop++;
		}
	}
	
}