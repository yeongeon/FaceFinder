package io.mulberry.facefinder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class FaceFinderApplication {
  private static Logger LOG = LoggerFactory.getLogger(FaceFinderApplication.class);

  static final Class<FaceFinderApplication> SELF_CLASS = FaceFinderApplication.class;
  static {
    System.load(SELF_CLASS.getResource("/lib/libopencv_java300.dylib").getPath());
  }

  static final boolean IS_PROCESSING_FOR_FACE_DETECT = false;

  public static void main(String[] args) throws IOException {
    List<Webcam> webcams = Webcam.getWebcams();
    LOG.info("webcams.size() : " + webcams.size());

    for (Webcam webcam : webcams) {
      if (webcam != null) {
        webcam.addWebcamListener(new WebcamListener() {
          @Override
          public void webcamOpen(WebcamEvent we) {
            LOG.info("Opened: " + we.getSource().getName() );
            WebcamViewer viewer = new WebcamViewer(we.getSource(), IS_PROCESSING_FOR_FACE_DETECT);
          }

          @Override
          public void webcamDisposed(WebcamEvent we) {
          }

          @Override
          public void webcamImageObtained(WebcamEvent we) {

          }

          @Override
          public void webcamClosed(WebcamEvent we) {

          }
        });
        LOG.info("Webcam: " + webcam.getName());
        Dimension viewSize = new Dimension(640, 480);
        webcam.setCustomViewSizes(new Dimension[] { viewSize });
        webcam.setViewSize(viewSize);
        webcam.open();
      } else {
        LOG.info("No webcam detected");
      }
    }
  }

}
