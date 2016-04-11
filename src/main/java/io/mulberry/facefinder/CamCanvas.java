package io.mulberry.facefinder;

import com.github.sarxos.webcam.Webcam;
import io.mulberry.facefinder.opencv.OpenCVFaceDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CamCanvas extends Canvas {
	private static Logger LOG = LoggerFactory.getLogger(CamCanvas.class);

	static final Class<CamCanvas> SELF_CLASS = CamCanvas.class;

	OpenCVFaceDetector<CamCanvas> faceDetector = new OpenCVFaceDetector<CamCanvas>(SELF_CLASS);
	
	private Webcam webcam = null;
	private boolean isProcessing = false;

	public CamCanvas(Webcam webcam){
		this(webcam, false);
	}
	
	public CamCanvas(Webcam webcam, boolean isProcessing){
		this.webcam = webcam;
		this.isProcessing = isProcessing;
	}

	@Override
	public void paint(Graphics g) {
    LOG.info("Let's begin to paint.");
		BufferedImage bimg = this.isProcessing?faceDetector.faceDetect(webcam.getImage()):webcam.getImage();
		g.drawImage(bimg, 0, 0, this);
	}

	@Override
	public void update(Graphics g) {
		BufferedImage bimg = this.isProcessing?faceDetector.faceDetect(webcam.getImage()):webcam.getImage();
		g.drawImage(bimg, 0, 0, this);
	}

}
