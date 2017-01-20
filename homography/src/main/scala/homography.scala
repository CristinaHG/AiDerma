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

    /*
    //mole2
    val file1 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/mole2.jpeg"
    val file2 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/425806267_118435_bigger_rotated.jpg"
    */

    //mole1
    val file1 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/mole1.jpeg"
    val file2 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/429721776_20725_bigger.jpg"


    val imgSrc: Mat = Imgcodecs.imread(file1)
    val imgDst: Mat = Imgcodecs.imread(file2)

    var pointsIm1 = List[Point]()
    var pointsIm2 = List[Point]()

    /*
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
    */

    //mole1
    var p11 = new Point(20,111)
    var p12 = new Point(213,20)
    var p13 = new Point(309,30)
    var p14 = new Point(414,113)
    var p15 = new Point(381,181)
    var p16 = new Point(133,144)

    pointsIm1 = (p11) :: pointsIm1
    pointsIm1 = (p12) :: pointsIm1
    pointsIm1 = (p13) :: pointsIm1
    pointsIm1 = (p14) :: pointsIm1
    pointsIm1 = (p15) :: pointsIm1
    pointsIm1 = (p16) :: pointsIm1
    pointsIm1 = pointsIm1.reverse


    //mole1
    var p21 = new Point(20,112)
    var p22 = new Point(212,20)
    var p23 = new Point(309,32)
    var p24 = new Point(415,115)
    var p25 = new Point(381,181)
    var p26 = new Point(136,141)

    pointsIm2 = (p21) :: pointsIm2
    pointsIm2 = (p22) :: pointsIm2
    pointsIm2 = (p23) :: pointsIm2
    pointsIm2 = (p24) :: pointsIm2
    pointsIm2 = (p25) :: pointsIm2
    pointsIm2 = (p26) :: pointsIm2
    pointsIm2 = pointsIm2.reverse


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
    //var rect: Rect = new Rect(Output.cols() / 2 - 125, Output.rows() / 2 - 125, 200, 200) //mole2
    var rect: Rect = new Rect(Output.cols() / 2 - 235, Output.rows() / 2 - 124, 450, 243) //mole1
    var roiImg = Output.submat(rect)
    var roiImgOrig = imgDst.submat(rect)
    Imgcodecs.imwrite("croppedHomo.png", roiImg)
    Imgcodecs.imwrite("croppedOrig.png", roiImgOrig)


    //load new images
    var mole1:Mat=Imgcodecs.imread("croppedHomo.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    var mole2:Mat=Imgcodecs.imread("croppedOrig.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)



    var diff: Mat = new Mat()
    //org.opencv.core.Core.absdiff(mole1,mole2, diff)
    //Imgcodecs.imwrite("byn.png", diff)
    //var withThreshold1:Mat=new Mat()
    //var withThreshold2:Mat=new Mat()
    //var withThreshold:Mat=new Mat()
    //org.opencv.imgproc.Imgproc.threshold(mole1,withThreshold1,150,255,org.opencv.imgproc.Imgproc.THRESH_TRUNC)
    //org.opencv.imgproc.Imgproc.threshold(mole2,withThreshold2,150,255,org.opencv.imgproc.Imgproc.THRESH_TRUNC)
//    var withThreshold1Adap:Mat=new Mat()
//    var withThreshold2Adap:Mat=new Mat()
//    org.opencv.imgproc.Imgproc.adaptiveThreshold(mole1,withThreshold1Adap,255, org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C,org.opencv.imgproc.Imgproc.THRESH_BINARY,
//      11,2)
//    org.opencv.imgproc.Imgproc.adaptiveThreshold(mole2,withThreshold2Adap,255,org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C,org.opencv.imgproc.Imgproc.THRESH_BINARY,
//      11,2)
//
//    Imgcodecs.imwrite("mole1ThersholdAdaptative.png",withThreshold1Adap)
//    Imgcodecs.imwrite("mole2ThersholdAdaptative.png",withThreshold2Adap)

    //Imgcodecs.imwrite("mole1Thershold.png",withThreshold1)
    //Imgcodecs.imwrite("mole2Thershold.png",withThreshold2)

    //finding contours
    var Binarized1:Mat=new Mat()
    var Binarized2:Mat=new Mat()
    var contours1 = new util.ArrayList[MatOfPoint]()
    var contours2 = new util.ArrayList[MatOfPoint]()
    //copmute thresholds
    org.opencv.imgproc.Imgproc.threshold(mole1,Binarized1,0,255,org.opencv.imgproc.Imgproc.THRESH_BINARY_INV+org.opencv.imgproc.Imgproc.THRESH_OTSU)
    org.opencv.imgproc.Imgproc.threshold(mole2,Binarized2,0,255,org.opencv.imgproc.Imgproc.THRESH_BINARY_INV+org.opencv.imgproc.Imgproc.THRESH_OTSU)
    //compute contours
    org.opencv.imgproc.Imgproc.findContours(Binarized1,contours1,new Mat(),org.opencv.imgproc.Imgproc.RETR_EXTERNAL,org.opencv.imgproc.Imgproc.CHAIN_APPROX_NONE)
    org.opencv.imgproc.Imgproc.findContours(Binarized2,contours2,new Mat(),org.opencv.imgproc.Imgproc.RETR_EXTERNAL,org.opencv.imgproc.Imgproc.CHAIN_APPROX_NONE)
    Imgcodecs.imwrite("shape1.png",Binarized1)
    Imgcodecs.imwrite("shape2.png",Binarized2)
    var cnt1=contours1.get(0)
    var cnt2=contours2.get(0)
    print("matching shapes="+ org.opencv.imgproc.Imgproc.matchShapes(cnt1,cnt2,org.opencv.imgproc.Imgproc.CV_CONTOURS_MATCH_I1,0))
    //draw polylines
    Binarized1.setTo(new Scalar(0))
    Binarized2.setTo(new Scalar(0))
    var newBinarized1:Mat=new Mat()
    var newBinarized2:Mat=new Mat()
    org.opencv.imgproc.Imgproc.cvtColor(Binarized1,newBinarized1,org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB)
    org.opencv.imgproc.Imgproc.cvtColor(Binarized2,newBinarized2,org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB)
    org.opencv.imgproc.Imgproc.polylines(newBinarized1,contours1,true,new Scalar(0,255,0))
    org.opencv.imgproc.Imgproc.polylines(newBinarized2,contours2,true,new Scalar(0,0,255))

    org.opencv.imgproc.Imgproc.polylines(roiImg,contours1,true,new Scalar(0,255,0))
    org.opencv.imgproc.Imgproc.polylines(roiImgOrig,contours2,true,new Scalar(0,0,255))


    Imgcodecs.imwrite("binarized1.png",roiImg)
    Imgcodecs.imwrite("binarized2.png",roiImgOrig)
    /*
    print(roiImg.channels())
    var transparent:Mat=new Mat(roiImg.size(),org.opencv.core.CvType.CV_8UC4)
    var alphaChannel:Mat=new Mat(roiImg.size(),org.opencv.core.CvType.CV_8UC1)

    alphaChannel.setTo(new Scalar(0.5))
    var rgb=new util.ArrayList[Mat]()
    rgb.add(alphaChannel)
   // print(rgb.size())
    org.opencv.core.Core.split(roiImg,rgb)
    org.opencv.core.Core.merge(rgb,transparent)

    //roiImg.convertTo(transparent,org.opencv.imgproc.Imgproc.COLOR_RGB2RGBA)
    //org.opencv.imgproc.Imgproc.cvtColor(roiImg,transparent,org.opencv.imgproc.Imgproc.COLOR_RGB2RGBA)


    Imgcodecs.imwrite("binarized1.png",transparent)
    */
    org.opencv.core.Core.absdiff(newBinarized1,newBinarized2, diff)
    Imgcodecs.imwrite("byn.png", diff)





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

    detector.detect(newBinarized1,kp1)
    detector.detect(newBinarized2,kp2)

    extractor.compute(newBinarized1,kp1,descriptors1)
    extractor.compute(newBinarized2,kp2,descriptors2)

    var matcher:org.opencv.features2d.DescriptorMatcher=DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING)

    var matches:MatOfDMatch=new MatOfDMatch()
    matcher.`match`(descriptors1,descriptors2,matches)

    var outputIMG:Mat=new Mat()
    var drawnMatches:MatOfByte  = new MatOfByte()
    org.opencv.features2d.Features2d.drawMatches(newBinarized1,kp1,newBinarized2,kp2,matches,outputIMG,new Scalar(255,0,0),new Scalar(0,255,0),drawnMatches,org.opencv.features2d.Features2d.NOT_DRAW_SINGLE_POINTS)

    Imgcodecs.imwrite("matches.png",outputIMG)

    //hough circle transformation
    var srcCoins=new Mat()
    var srcCoinsGray=new Mat()

    srcCoins=org.opencv.imgcodecs.Imgcodecs.imread("/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/coins.jpg")
    org.opencv.imgproc.Imgproc.cvtColor(srcCoins,srcCoinsGray,org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY)
    //reduce the noise
    org.opencv.imgproc.Imgproc.GaussianBlur(srcCoinsGray,srcCoinsGray,new Size(9,9),2,2)

    var circles=new Mat()
    org.opencv.imgproc.Imgproc.HoughCircles(srcCoinsGray,circles,org.opencv.imgproc.Imgproc.CV_HOUGH_GRADIENT,1,srcCoinsGray.rows()/8, 200,100,0,0)

    var x:Int=0
    /// Draw the circles detected
    for (x<-0 until circles.cols() ){
      var vCircle=circles.get(0,x)
      var center:Point=new Point(Math.round(vCircle(0)),Math.round(vCircle(1)))
      var ratio=Math.round(vCircle(2)).toInt
      //draw the found circle
      org.opencv.imgproc.Imgproc.circle(srcCoins,center,ratio,new Scalar(0,255,255),3)

    }
    Imgcodecs.imwrite("HoughCoins.png",srcCoins)

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
