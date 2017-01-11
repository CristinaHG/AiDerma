/**
  * Created by cris on 2/01/17.
  */


import java.io.File
import java.util
import javax.swing.text.html.ImageView

import org.opencv.calib3d.Calib3d
import org.opencv.core._
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.features2d.{DescriptorExtractor, DescriptorMatcher, FeatureDetector}
import org.opencv.imgproc.Imgproc
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.objdetect.CascadeClassifier
import org.opencv.imgproc.Imgproc.rectangle
import scala.collection.JavaConverters._
class homography {
  def run = {
    val file1 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/mole2.jpeg"
    val file2 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/425806267_118435_bigger_rotated.jpg"

    val imgSrc: Mat = Imgcodecs.imread(file1)
    val imgDst: Mat = Imgcodecs.imread(file2)

    var pointsIm1 = List[Point]()
    //calculattor1 points
    //var p11 = new Point(148, 208)
    //var p12 = new Point(568, 210)
    //var p13 = new Point(144, 1042)
    //var p14 = new Point(542, 1058)
    //var p15 = new Point(180, 588)
    //var p16 = new Point(176, 808)

    //mole3 points
    //var p11 = new Point(180,114)
    //var p12 = new Point(219, 93)
    //var p13 = new Point(218,161)
    //var p14 = new Point(251,141)
    //var p15 = new Point(216,73)
    //var p16 = new Point(217,48)

    //mole1
    //var p11 = new Point(19,115)
    //var p12 = new Point(49,82)
    //var p13 = new Point(336,35)
    //var p14 = new Point(381,181)
    //var p15 = new Point(177,174)
    //var p16 = new Point(123,183)

    //mole2
    var p11 = new Point(438, 298)
    var p12 = new Point(435, 274)
    var p13 = new Point(377, 264)
    var p14 = new Point(378, 309)
    var p15 = new Point(405, 284)
    var p16 = new Point(413, 317)

    pointsIm1 = (p11) :: pointsIm1
    pointsIm1 = (p12) :: pointsIm1
    pointsIm1 = (p13) :: pointsIm1
    pointsIm1 = (p14) :: pointsIm1
    pointsIm1 = (p15) :: pointsIm1
    pointsIm1 = (p16) :: pointsIm1
    pointsIm1 = pointsIm1.reverse

    var pointsIm2 = List[Point]()
    //calculator2 points
    //var p21 = new Point(394, 192)
    //var p22 = new Point(708, 362)
    //var p23 = new Point(26, 832)
    //var p24 = new Point(342, 1024)
    //var p25 = new Point(254, 488)
    //var p26 = new Point(156, 654)

    //mole1
    //var p21 = new Point(20,117)
    //var p22 = new Point(150,82)
    //var p23 = new Point(338,34)
    //var p24 = new Point(381,182)
    //var p25 = new Point(178,172)
    //var p26 = new Point(123,181)

    //mole2
    var p21 = new Point(351, 240)
    var p22 = new Point(354, 267)
    var p23 = new Point(421, 276)
    var p24 = new Point(419, 226)
    var p25 = new Point(386, 253)
    var p26 = new Point(377, 217)

    pointsIm2 = (p21) :: pointsIm2
    pointsIm2 = (p22) :: pointsIm2
    pointsIm2 = (p23) :: pointsIm2
    pointsIm2 = (p24) :: pointsIm2
    pointsIm2 = (p25) :: pointsIm2
    pointsIm2 = (p26) :: pointsIm2
    pointsIm2 = pointsIm2.reverse

    //var Output:Mat=new Mat()
    var dst: MatOfPoint2f = new MatOfPoint2f()
    var src: MatOfPoint2f = new MatOfPoint2f()

    dst.fromList(pointsIm2.asJava)
    src.fromList(pointsIm1.asJava)


    var h: Mat = Calib3d.findHomography(src, dst, org.opencv.calib3d.Calib3d.RANSAC, 10)

    var Output: Mat = new Mat()
    Imgproc.warpPerspective(imgSrc, Output, h, imgSrc.size())

    val filename = "output.png"
    val f1 = "img1.png"
    val f2 = "img2.png"
    Imgcodecs.imwrite(filename, Output)
    Imgcodecs.imwrite(f1, imgSrc)
    Imgcodecs.imwrite(f2, imgDst)

    var cropped = new Mat()
    var rect: Rect = new Rect(Output.cols() / 2 - 125, Output.rows() / 2 - 125, 200, 200)
    //var roi=rectangle(Output,new Point(332,232), new Point(334,337),new Scalar(0,255,0) ,3)
    var roiImg = Output.submat(rect)
    var roiImgOrig = imgDst.submat(rect)
    Imgcodecs.imwrite("croppedHomo.png", roiImg)
    Imgcodecs.imwrite("croppedOrig.png", roiImgOrig)


    //load new images
    var mole1:Mat=Imgcodecs.imread("croppedHomo.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    var mole2:Mat=Imgcodecs.imread("croppedOrig.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    var diff: Mat = new Mat()
    org.opencv.core.Core.absdiff(mole1,mole2, diff)
    Imgcodecs.imwrite("byn.png", diff)
    var withThreshold1:Mat=new Mat()
    var withThreshold2:Mat=new Mat()
    var withThreshold:Mat=new Mat()
    org.opencv.imgproc.Imgproc.threshold(mole1,withThreshold1,150,255,org.opencv.imgproc.Imgproc.THRESH_TRUNC)
    org.opencv.imgproc.Imgproc.threshold(mole2,withThreshold2,150,255,org.opencv.imgproc.Imgproc.THRESH_TRUNC)
//    var withThreshold1Adap:Mat=new Mat()
//    var withThreshold2Adap:Mat=new Mat()
//    org.opencv.imgproc.Imgproc.adaptiveThreshold(mole1,withThreshold1Adap,255, org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C,org.opencv.imgproc.Imgproc.THRESH_BINARY,
//      11,2)
//    org.opencv.imgproc.Imgproc.adaptiveThreshold(mole2,withThreshold2Adap,255,org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C,org.opencv.imgproc.Imgproc.THRESH_BINARY,
//      11,2)
//
//    Imgcodecs.imwrite("mole1ThersholdAdaptative.png",withThreshold1Adap)
//    Imgcodecs.imwrite("mole2ThersholdAdaptative.png",withThreshold2Adap)

    Imgcodecs.imwrite("mole1Thershold.png",withThreshold1)
    Imgcodecs.imwrite("mole2Thershold.png",withThreshold2)

    //finding contours
    var Binarized1:Mat=new Mat()
    var Binarized2:Mat=new Mat()
    var contours = new util.ArrayList[MatOfPoint]();

    org.opencv.imgproc.Imgproc.threshold(withThreshold1,Binarized1,127,255,org.opencv.imgproc.Imgproc.THRESH_BINARY_INV)
    org.opencv.imgproc.Imgproc.threshold(withThreshold2,Binarized2,127,255,org.opencv.imgproc.Imgproc.THRESH_BINARY_INV)
    var contours1=org.opencv.imgproc.Imgproc.findContours(Binarized1,contours,new Mat(),org.opencv.imgproc.Imgproc.RETR_EXTERNAL,org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE)
    //org.opencv.imgproc.Imgproc.drawContours(Binarized1,contours,1,new Scalar(200,200,200),2)
    Binarized1.setTo(new Scalar(0))
    org.opencv.imgproc.Imgproc.polylines(Binarized1,contours,true,new Scalar(0),-1)

    Imgcodecs.imwrite("binarized1.png",Binarized1)
    Imgcodecs.imwrite("binarized2.png",Binarized2)

    org.opencv.core.Core.absdiff(withThreshold1,withThreshold2, withThreshold)
    Imgcodecs.imwrite("diff.png", withThreshold)

    //keypoints

    //load new images
    //var mole1:Mat=Imgcodecs.imread("croppedHomo.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    //var mole2:Mat=Imgcodecs.imread("croppedOrig.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)

    //Imgcodecs.imwrite("gray.png",mole1)

    var kp1:MatOfKeyPoint=new MatOfKeyPoint()
    var kp2:MatOfKeyPoint=new MatOfKeyPoint()
    var descriptors1:Mat=new Mat()
    var descriptors2:Mat=new Mat()

    var detector:FeatureDetector=FeatureDetector.create(FeatureDetector.ORB)
    var extractor:DescriptorExtractor=DescriptorExtractor.create(DescriptorExtractor.ORB)

    detector.detect(mole1,kp1)
    detector.detect(mole2,kp2)

    extractor.compute(mole1,kp1,descriptors1)
    extractor.compute(mole2,kp2,descriptors2)

    var matcher:org.opencv.features2d.DescriptorMatcher=DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING)

    var matches:MatOfDMatch=new MatOfDMatch()
    matcher.`match`(descriptors1,descriptors2,matches)

    var outputIMG:Mat=new Mat()
    var drawnMatches:MatOfByte  = new MatOfByte()
    org.opencv.features2d.Features2d.drawMatches(mole1,kp1,mole2,kp2,matches,outputIMG,new Scalar(255,0,0),new Scalar(0,255,0),drawnMatches,org.opencv.features2d.Features2d.NOT_DRAW_SINGLE_POINTS)

    Imgcodecs.imwrite("matches.png",outputIMG)



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
