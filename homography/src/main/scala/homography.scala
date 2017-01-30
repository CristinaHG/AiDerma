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

import scala.math.BigDecimal
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.objdetect.CascadeClassifier
import org.opencv.imgproc.Imgproc.rectangle
import scala.collection.JavaConverters._
import scala.math.BigDecimal



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
    var DstCop=new Mat
    imgDst.copyTo(DstCop)
    val imgSrcCopy: Mat = Imgcodecs.imread(file1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    val imgDstCopy: Mat = Imgcodecs.imread(file2, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)

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
//    var p11 = new Point(20, 111)
//    var p12 = new Point(213, 20)
//    var p13 = new Point(309, 30)
//    var p14 = new Point(414, 113)
//    var p15 = new Point(381, 181)
//    var p16 = new Point(133, 144)
//
//    pointsIm1 = (p11) :: pointsIm1
//    pointsIm1 = (p12) :: pointsIm1
//    pointsIm1 = (p13) :: pointsIm1
//    pointsIm1 = (p14) :: pointsIm1
//    pointsIm1 = (p15) :: pointsIm1
//    pointsIm1 = (p16) :: pointsIm1
//    pointsIm1 = pointsIm1.reverse
//
//
//    //mole1
//    var p21 = new Point(20, 112)
//    var p22 = new Point(212, 20)
//    var p23 = new Point(309, 32)
//    var p24 = new Point(415, 115)
//    var p25 = new Point(381, 181)
//    var p26 = new Point(136, 141)
//
//    pointsIm2 = (p21) :: pointsIm2
//    pointsIm2 = (p22) :: pointsIm2
//    pointsIm2 = (p23) :: pointsIm2
//    pointsIm2 = (p24) :: pointsIm2
//    pointsIm2 = (p25) :: pointsIm2
//    pointsIm2 = (p26) :: pointsIm2
//    pointsIm2 = pointsIm2.reverse

    var p11 = new Point(52,102)
    var p12 = new Point(210,152)
    var p13 = new Point(309,111)
    var p14 = new Point(383,77)
    var p15 = new Point(408,101)
    var p16 = new Point(389,117)
    var p17= new Point(384,131)

    pointsIm1 = (p11) :: pointsIm1
    pointsIm1 = (p12) :: pointsIm1
    pointsIm1 = (p13) :: pointsIm1
    pointsIm1 = (p14) :: pointsIm1
    pointsIm1 = (p15) :: pointsIm1
    pointsIm1 = (p16) :: pointsIm1
    pointsIm1 = pointsIm1.reverse


    //mole1
    var p21 = new Point(52,102)
    var p22 = new Point(211,153)
    var p23 = new Point(310,111)
    var p24 = new Point(383,76)
    var p25 = new Point(407,101)
    var p26 = new Point(389,118)

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



    var h: Mat = Calib3d.findHomography(dst, src, org.opencv.calib3d.Calib3d.RANSAC, 10)

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
    var rect: Rect = new Rect(Output.cols() / 2 - 235, Output.rows() / 2 - 124, 450, 243)
    //mole1
    var roiImg = Output.submat(rect)
    var roiImgOrig = imgDst.submat(rect)
    Imgcodecs.imwrite("croppedHomo.png", roiImg)
    Imgcodecs.imwrite("croppedOrig.png", roiImgOrig)


//    //load new images
//    var mole1: Mat = Imgcodecs.imread("croppedHomo.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
//    var mole2: Mat = Imgcodecs.imread("croppedOrig.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
//
//
//    var diff: Mat = new Mat()
//
//
//    //finding contours
//    var Binarized1: Mat = new Mat()
//    var Binarized2: Mat = new Mat()
//    var contours1 = new util.ArrayList[MatOfPoint]()
//    var contours2 = new util.ArrayList[MatOfPoint]()
//    //copmute thresholds
//    org.opencv.imgproc.Imgproc.threshold(mole1, Binarized1, 0, 255, org.opencv.imgproc.Imgproc.THRESH_BINARY_INV + org.opencv.imgproc.Imgproc.THRESH_OTSU)
//    org.opencv.imgproc.Imgproc.threshold(mole2, Binarized2, 0, 255, org.opencv.imgproc.Imgproc.THRESH_BINARY_INV + org.opencv.imgproc.Imgproc.THRESH_OTSU)
//    //compute contours
//    org.opencv.imgproc.Imgproc.findContours(Binarized1, contours1, new Mat(), org.opencv.imgproc.Imgproc.RETR_EXTERNAL, org.opencv.imgproc.Imgproc.CHAIN_APPROX_NONE)
//    org.opencv.imgproc.Imgproc.findContours(Binarized2, contours2, new Mat(), org.opencv.imgproc.Imgproc.RETR_EXTERNAL, org.opencv.imgproc.Imgproc.CHAIN_APPROX_NONE)
//    Imgcodecs.imwrite("shape1.png", Binarized1)
//    Imgcodecs.imwrite("shape2.png", Binarized2)
//    var cnt1 = contours1.get(0)
//    var cnt2 = contours2.get(0)
//    print("matching shapes=" + org.opencv.imgproc.Imgproc.matchShapes(cnt1, cnt2, org.opencv.imgproc.Imgproc.CV_CONTOURS_MATCH_I1, 0))
//    //draw polylines
//    Binarized1.setTo(new Scalar(0))
//    Binarized2.setTo(new Scalar(0))
//    var newBinarized1: Mat = new Mat()
//    var newBinarized2: Mat = new Mat()
//    org.opencv.imgproc.Imgproc.cvtColor(Binarized1, newBinarized1, org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB)
//    org.opencv.imgproc.Imgproc.cvtColor(Binarized2, newBinarized2, org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB)
//    org.opencv.imgproc.Imgproc.polylines(newBinarized1, contours1, true, new Scalar(0, 255, 0))
//    org.opencv.imgproc.Imgproc.polylines(newBinarized2, contours2, true, new Scalar(0, 0, 255))
//
//    org.opencv.imgproc.Imgproc.polylines(roiImg, contours1, true, new Scalar(0, 255, 0))
//    org.opencv.imgproc.Imgproc.polylines(roiImgOrig, contours2, true, new Scalar(0, 0, 255))
//
//
//    Imgcodecs.imwrite("binarized1.png", roiImg)
//    Imgcodecs.imwrite("binarized2.png", roiImgOrig)
//
//    org.opencv.core.Core.absdiff(newBinarized1, newBinarized2, diff)
//    Imgcodecs.imwrite("byn.png", diff)
//

    // getting keypoints

    var kp1: MatOfKeyPoint = new MatOfKeyPoint()
    var kp2: MatOfKeyPoint = new MatOfKeyPoint()
    var descriptors1: Mat = new Mat()
    var descriptors2: Mat = new Mat()

    var detector: FeatureDetector = FeatureDetector.create(FeatureDetector.ORB)
    var extractor: DescriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB)

    Imgcodecs.imwrite("srcCopy.png", imgSrcCopy)
    Imgcodecs.imwrite("dstCopy.png", imgDstCopy)

    detector.detect(imgSrcCopy, kp1)
    detector.detect(imgDstCopy, kp2)

    extractor.compute(imgSrcCopy, kp1, descriptors1)
    extractor.compute(imgDstCopy, kp2, descriptors2)

    var matcher: org.opencv.features2d.DescriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED)

    var matches: MatOfDMatch = new MatOfDMatch()

    if (descriptors1.`type`() != CvType.CV_32F) descriptors1.convertTo(descriptors1, CvType.CV_32F)
    if (descriptors2.`type`() != CvType.CV_32F) descriptors2.convertTo(descriptors2, CvType.CV_32F)
    matcher.`match`(descriptors1, descriptors2, matches)

    //filtering best matches
    var bestMatches: MatOfDMatch = new MatOfDMatch()
    var matchesCopy = matches
    var ordered = matches.toArray.sortWith(_.distance < _.distance).take(7)
    ordered.foreach(f => {
      var indexInMatches = f.queryIdx
      bestMatches.push_back(matchesCopy.row(indexInMatches))
    })

    var outputIMG: Mat = new Mat()
    var drawnMatches: MatOfByte = new MatOfByte()
    org.opencv.features2d.Features2d.drawMatches(imgSrcCopy, kp1, imgDstCopy, kp2, bestMatches, outputIMG, new Scalar(255, 0, 0), new Scalar(0, 255, 0), drawnMatches, org.opencv.features2d.Features2d.NOT_DRAW_SINGLE_POINTS)

    Imgcodecs.imwrite("matchesFAST_ORBReduced.png", outputIMG)



    //geting pixels from descriptors matches

    var lp1= List[Point]()
    var lp2= List[Point]()
    var prevKP=kp1.toList
    var actKP=kp2.toList
    var copySRC=new Mat()
    imgSrc.copyTo(copySRC)

    bestMatches.toArray.foreach(f => {

      lp1=lp1:+ prevKP.get(f.queryIdx).pt
      lp2= lp2:+ actKP.get(f.trainIdx).pt
      org.opencv.imgproc.Imgproc.circle(imgSrc,prevKP.get(f.queryIdx).pt,1,new Scalar(0,255,0))
  //    print("kp1"+prevKP.get(f.queryIdx).pt + "\n")
      org.opencv.imgproc.Imgproc.circle(DstCop ,actKP.get(f.trainIdx).pt,1,new Scalar(0,255,0))
   //   print("kp2"+actKP.get(f.queryIdx).pt+ "\n")
//            org.opencv.imgproc.Imgproc.rectangle(imgSrc,prevKP{f.queryIdx}.pt,prevKP{f.queryIdx}.pt,new Scalar(0,255,0))
//            org.opencv.imgproc.Imgproc.rectangle(DstCop ,actKP{f.trainIdx}.pt,actKP{f.trainIdx}.pt,new Scalar(0,255,0))

    })


    Imgcodecs.imwrite("srcMatchedPixels.png", imgSrc)
    Imgcodecs.imwrite("DstMatchedPixels.png", DstCop)

    var dstMat: MatOfPoint2f = new MatOfPoint2f()
    var srcMat: MatOfPoint2f = new MatOfPoint2f()

    dstMat.fromList(lp1.asJava)
    srcMat.fromList(lp2.asJava)

    var h1: Mat = Calib3d.findHomography(srcMat,dstMat, org.opencv.calib3d.Calib3d.RANSAC, 10)

    var Output1: Mat = new Mat()
    Imgcodecs.imwrite("SRCOPY.png",copySRC)

    Imgproc.warpPerspective(copySRC, Output1, h1, copySRC.size())

    Imgcodecs.imwrite("homographyMatches.png", Output1)

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



