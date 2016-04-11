package io.mulberry.facefinder;

import com.github.sarxos.webcam.Webcam;
import io.mulberry.facefinder.thread.CamImageThread;

import java.awt.*;

public class WebcamViewer extends Frame {
	private final Webcam webcam;
	private final boolean isProcessing;
	private FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	public WebcamViewer(Webcam webcam, boolean isProcessing) {
		super("WebcamViewer");
		this.webcam = webcam;
		this.isProcessing = isProcessing;
		init();
		super.setSize(640, 480);
		Dimension f1_size = super.getSize();
		int left = (screen.width / 2) - (f1_size.width / 2);
		int top = (screen.height / 2) - (f1_size.height / 2);
		super.setLocation(left, top);
		super.setResizable(true);
		super.setVisible(true);
	}

	private void init() {
		CamCanvas canvas = new CamCanvas(this.webcam, this.isProcessing);
		
		canvas.setSize(screen);
		super.add(canvas);
		canvas.setBackground(Color.BLUE);
		canvas.setVisible(true);

		new CamImageThread(webcam, canvas, false, false).start();
	}
}
