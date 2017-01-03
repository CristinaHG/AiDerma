/**
  * Created by cris on 2/01/17.
  */


import java.io.File
import javax.swing.text.html.ImageView

import org.opencv.calib3d.Calib3d
import org.opencv.core._
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.objdetect.CascadeClassifier
import org.opencv.imgproc.Imgproc.rectangle
import scala.collection.JavaConverters._
class homography {
  def run= {
    val file1 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/homography/src/img/calculator1.jpg"
    val file2 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/homography/src/img/calculator2.jpg"

    val imgSrc: Mat = Imgcodecs.imread(file1)
    val imgDst: Mat = Imgcodecs.imread(file2)

    var pointsIm1 = List[Point]()
    var p11 = new Point(148, 208)
    var p12 = new Point(568, 210)
    var p13 = new Point(144, 1042)
    var p14 = new Point(542, 1058)
    var p15 = new Point(180, 588)
    var p16 = new Point(176, 808)
    pointsIm1=(p11):: pointsIm1
    pointsIm1=(p12)::pointsIm1
    pointsIm1=(p13)::pointsIm1
    pointsIm1=(p14)::pointsIm1
    pointsIm1=(p15)::pointsIm1
    pointsIm1=(p16)::pointsIm1
    pointsIm1=pointsIm1.reverse

    var pointsIm2 = List[Point]()
    var p21 = new Point(394, 192)
    var p22 = new Point(708, 362)
    var p23 = new Point(26, 832)
    var p24 = new Point(342, 1024)
    var p25 = new Point(254, 488)
    var p26 = new Point(156, 654)
    pointsIm2=(p21)::pointsIm2
    pointsIm2=(p22)::pointsIm2
    pointsIm2=(p23)::pointsIm2
    pointsIm2=(p24)::pointsIm2
    pointsIm2=(p25)::pointsIm2
    pointsIm2=(p26)::pointsIm2
    pointsIm2=pointsIm2.reverse

    //var Output:Mat=new Mat()
    var dst: MatOfPoint2f = new MatOfPoint2f()
    var src: MatOfPoint2f = new MatOfPoint2f()

    dst.fromList(pointsIm2.asJava)
    src.fromList(pointsIm1.asJava)


    var h: Mat = Calib3d.findHomography(src, dst, org.opencv.calib3d.Calib3d.RANSAC, 10)

    var Output: Mat = new Mat()
    Imgproc.warpPerspective(imgSrc, Output, h, imgSrc.size())

    val filename="output.png"
    val f1="img1.png"
    val f2="img2.png"
    Imgcodecs.imwrite(filename, Output)
    Imgcodecs.imwrite(f1,imgSrc)
    Imgcodecs.imwrite(f2,imgDst)

  }
}

object MyHomography{

  def main(args: Array[String]): Unit
  =
  {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    var h1=new homography
    h1.run
  }
}
