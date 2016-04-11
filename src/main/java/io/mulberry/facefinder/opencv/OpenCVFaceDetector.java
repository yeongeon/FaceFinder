package io.mulberry.facefinder.opencv;

import org.opencv.core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class OpenCVFaceDetector<T> {
	private static Logger LOG = LoggerFactory.getLogger(OpenCVFaceDetector.class);

	private final CascadeClassifier faceDetector;

	public OpenCVFaceDetector(Class<T> self_class){
		String s = self_class.getResource("/data/haarcascades/haarcascade_frontalface_alt.xml").getPath();
		faceDetector = new CascadeClassifier(s);
	}

	private int detecedFaces = 0;
	
	public int featureDetect(String input, String output) {
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.FAST);
		Mat image = Imgcodecs.imread(input);

		MatOfKeyPoint keypoints = new MatOfKeyPoint();

		Mat rgb = new Mat();
		Imgproc.cvtColor(image, rgb, Imgproc.COLOR_RGBA2RGB);
		detector.detect(rgb, keypoints);
		Features2d.drawKeypoints(rgb, keypoints, rgb);

		return 0;
	}

	public int faceDetect(String input, String output) {
		LOG.info("input: "+ input);
		LOG.info("output: "+ output);

		Mat image = Imgcodecs.imread(input);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		int detects = faceDetections.toArray().length;
		LOG.info("Detected %s faces", detects);

		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}

		LOG.info("Write on %s", output);
		Imgcodecs.imwrite(output, image);

		return detects;
	}

	public BufferedImage faceDetect(BufferedImage bimg) {
		byte[] data = ((DataBufferByte) bimg.getRaster().getDataBuffer())
				.getData();
		Mat image = new Mat(bimg.getHeight(), bimg.getWidth(), CvType.CV_8UC3);
		image.put(0, 0, data);

		// ---------------------------------------------
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		int detects = faceDetections.toArray().length;
		if(detecedFaces != detects){
			detecedFaces = detects;
			LOG.info("Detected %s faces", detects);
		}

		Mat image2 = new Mat(bimg.getHeight(), bimg.getWidth(), CvType.CV_8UC3);
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image2, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(255, 0, 0));
		}
		// ---------------------------------------------

		byte[] mdata = new byte[image2.rows() * image2.cols()
				* (int) (image2.elemSize())];
		image2.get(0, 0, mdata);
		if (image2.channels() == 3) {
			for (int i = 0; i < mdata.length; i += 3) {
				byte temp = mdata[i];
				mdata[i] = mdata[i + 2];
				mdata[i + 2] = temp;
			}
		}
		BufferedImage bimage = new BufferedImage(image2.cols(), image2.rows(),
				BufferedImage.TYPE_3BYTE_BGR);
		bimage.getRaster().setDataElements(0, 0, image2.cols(), image2.rows(),
				mdata);
		return bimage;
	}
}