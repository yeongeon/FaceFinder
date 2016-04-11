package io.mulberry.facefinder.opencv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainOpenCV {
	private static Logger LOG = LoggerFactory.getLogger(MainOpenCV.class);
	static final Class<MainOpenCV> SELF_CLASS = MainOpenCV.class;

	static {
		try{
			String s = SELF_CLASS.getResource("/lib/libopencv_java300.dylib").getPath();
			System.load(s);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		OpenCVFaceDetector<MainOpenCV> faceDetector = new OpenCVFaceDetector<MainOpenCV>(SELF_CLASS);
		String input = SELF_CLASS.getResource("/output/frame_4190.png").getPath();
		String output = SELF_CLASS.getResource("/output/frame_10280.png").getPath();
		int detects = faceDetector.faceDetect(input, output);
		LOG.info("++ faceDetect >> "+ detects);
	}



}
