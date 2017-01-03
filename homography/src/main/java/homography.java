/**
  * Created by cris on 2/01/17.
  */

/*
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.LinkedList;

import static org.opencv.imgproc.Imgproc.rectangle;

public class homography {
  public void run() {
    String file1 = "/home/cris/opencv-3.2.0/samples/java/homography/src/img/calculator1.jpg";
    String file2 = "/home/cris/opencv-3.2.0/samples/java/homography/src/img/calculator2.jpg";

    Mat imgSrc = Imgcodecs.imread(file1);
    Mat imgDst = Imgcodecs.imread(file2);

    Point [] srcArray= new Point[6];
    Point p11 = new Point(148, 208);
    Point p12 = new Point(568, 210);
    Point p13 = new Point(144, 1042);
    Point p14 = new Point(542, 1058);
    Point p15 = new Point(180, 588);
    Point p16 = new Point(176, 808);

    srcArray[0]=p11;
    srcArray[1]=p12;
    srcArray[2]=p13;
    srcArray[3]=p14;
    srcArray[4]=p15;
    srcArray[5]=p16;

    Mat OutputMat = new Mat();
    LinkedList<Point> dstArray = new LinkedList<Point>();

    Point p21 = new Point(394, 192);
    Point p22 = new Point(708, 362);
    Point p23 = new Point(26, 832);
    Point p24 = new Point(342, 1024);
    Point p25 = new Point(254, 488);
    Point p26 = new Point(156, 654);

    dstArray.add(p21);
    dstArray.add(p22);
    dstArray.add(p23);
    dstArray.add(p24);
    dstArray.add(p25);
    dstArray.add(p26);

    //Mat Output=new Mat();
     MatOfPoint2f dst= new MatOfPoint2f();
     MatOfPoint2f src= new MatOfPoint2f();

    dst.fromList(dstArray);
    src.fromArray(srcArray);


     Mat h = org.opencv.calib3d.Calib3d.findHomography(src, dst, Calib3d.RANSAC,10);

    Imgproc.warpPerspective(imgSrc, OutputMat, h, imgSrc.size());

    String filename = "output.png";
    String f1="img1.png";
    String f2="im2.png";
    Imgcodecs.imwrite(filename, OutputMat);
    Imgcodecs.imwrite(f1, imgSrc);
    Imgcodecs.imwrite(f2, imgDst);
  }
}

class MyHomography{

  public static void main(String[] args)
  {
    System.out.println("hi homography");
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    new homography().run();
  }
}

*/