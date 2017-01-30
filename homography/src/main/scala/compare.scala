/**
  * Created by cris on 30/01/17.
  */
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



class compare {

  def run = {


    //mole2
    val file1 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/mole2cropped.jpeg"
    val file2 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/425806267_118435_bigger_rotatedcropped.jpg"


//    //mole1
//    val file1 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/mole1.jpeg"
//    val file2 = "/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/429721776_20725_bigger.jpg"


    val imgSrc: Mat = Imgcodecs.imread(file1)
    val imgDst: Mat = Imgcodecs.imread(file2)

    val imgSrcCopy: Mat = Imgcodecs.imread(file1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    val imgDstCopy: Mat = Imgcodecs.imread(file2, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)


    // getting  the keypoints

    var kp1: MatOfKeyPoint = new MatOfKeyPoint()
    var kp2: MatOfKeyPoint = new MatOfKeyPoint()
    var descriptors1: Mat = new Mat()
    var descriptors2: Mat = new Mat()

    var detector: FeatureDetector = FeatureDetector.create(FeatureDetector.ORB)
    var extractor: DescriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB)

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

    var lp1 = List[Point]()
    var lp2 = List[Point]()
    var prevKP = kp1.toList
    var actKP = kp2.toList
    var coloredPixelsSrc=new Mat()
    var coloredPixelsDst=new Mat()
    imgSrc.copyTo(coloredPixelsSrc)
    imgDst.copyTo(coloredPixelsDst)

    bestMatches.toArray.foreach(f => {
      lp1 = lp1 :+ prevKP.get(f.queryIdx).pt
      lp2 = lp2 :+ actKP.get(f.trainIdx).pt
      org.opencv.imgproc.Imgproc.circle(coloredPixelsSrc, prevKP.get(f.queryIdx).pt, 1, new Scalar(0, 255, 0))
      org.opencv.imgproc.Imgproc.circle(coloredPixelsDst, actKP.get(f.trainIdx).pt, 1, new Scalar(0, 255, 0))
    })


    Imgcodecs.imwrite("srcMatchedPixels.png", coloredPixelsSrc)
    Imgcodecs.imwrite("DstMatchedPixels.png", coloredPixelsDst)


    //compute homography with calculated points

    var dstMat: MatOfPoint2f = new MatOfPoint2f()
    var srcMat: MatOfPoint2f = new MatOfPoint2f()

    dstMat.fromList(lp1.asJava)
    srcMat.fromList(lp2.asJava)

    var h1: Mat = Calib3d.findHomography(srcMat, dstMat, org.opencv.calib3d.Calib3d.RANSAC, 10)

    var Output: Mat = new Mat()
    Imgproc.warpPerspective(imgSrc, Output, h1, imgSrc.size())

    Imgcodecs.imwrite("homographyMatches.png", Output)


    //crop images to compare
    var cropped = new Mat()
    var rect: Rect = new Rect(Output.cols() / 2 -70, Output.rows() / 2 -70 , 110, 110) //mole2
    //var rect: Rect = new Rect(Output.cols() / 2 - 235, Output.rows() / 2 - 124, 450, 243) //mole1
    //mole1
    var roiImg = Output.submat(rect)
    var roiImgOrig = imgDst.submat(rect)
    Imgcodecs.imwrite("croppedHomo.png", roiImg)
    Imgcodecs.imwrite("croppedOrig.png", roiImgOrig)


    //load new images
    var mole1: Mat = Imgcodecs.imread("croppedHomo.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)
    var mole2: Mat = Imgcodecs.imread("croppedOrig.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)

    //finding contours
    var diff: Mat = new Mat()
    var Binarized1: Mat = new Mat()
    var Binarized2: Mat = new Mat()
    var contours1 = new util.ArrayList[MatOfPoint]()
    var contours2 = new util.ArrayList[MatOfPoint]()

    //compute thresholds
    org.opencv.imgproc.Imgproc.threshold(mole1, Binarized1, 0, 255, org.opencv.imgproc.Imgproc.THRESH_BINARY_INV + org.opencv.imgproc.Imgproc.THRESH_OTSU)
    org.opencv.imgproc.Imgproc.threshold(mole2, Binarized2, 0, 255, org.opencv.imgproc.Imgproc.THRESH_BINARY_INV + org.opencv.imgproc.Imgproc.THRESH_OTSU)
    //compute contours
    org.opencv.imgproc.Imgproc.findContours(Binarized1, contours1, new Mat(), org.opencv.imgproc.Imgproc.RETR_EXTERNAL, org.opencv.imgproc.Imgproc.CHAIN_APPROX_NONE)
    org.opencv.imgproc.Imgproc.findContours(Binarized2, contours2, new Mat(), org.opencv.imgproc.Imgproc.RETR_EXTERNAL, org.opencv.imgproc.Imgproc.CHAIN_APPROX_NONE)
    Imgcodecs.imwrite("shape1.png", Binarized1)
    Imgcodecs.imwrite("shape2.png", Binarized2)
    var cnt1 = contours1.get(0)
    var cnt2 = contours2.get(0)
    print("matching shapes=" + org.opencv.imgproc.Imgproc.matchShapes(cnt1, cnt2, org.opencv.imgproc.Imgproc.CV_CONTOURS_MATCH_I1, 0))
    //draw polylines
    Binarized1.setTo(new Scalar(0))
    Binarized2.setTo(new Scalar(0))
    var newBinarized1: Mat = new Mat()
    var newBinarized2: Mat = new Mat()
    org.opencv.imgproc.Imgproc.cvtColor(Binarized1, newBinarized1, org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB)
    org.opencv.imgproc.Imgproc.cvtColor(Binarized2, newBinarized2, org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB)
    org.opencv.imgproc.Imgproc.polylines(newBinarized1, contours1, true, new Scalar(0, 255, 0))
    org.opencv.imgproc.Imgproc.polylines(newBinarized2, contours2, true, new Scalar(0, 0, 255))

    org.opencv.imgproc.Imgproc.polylines(roiImg, contours1, true, new Scalar(0, 255, 0))
    org.opencv.imgproc.Imgproc.polylines(roiImgOrig, contours2, true, new Scalar(0, 0, 255))


    Imgcodecs.imwrite("binarized1.png", roiImg)
    Imgcodecs.imwrite("binarized2.png", roiImgOrig)
    //show difference
    org.opencv.core.Core.absdiff(newBinarized1, newBinarized2, diff)
    Imgcodecs.imwrite("byn.png", diff)

  }
}

    object MyComparation{

  def main(args: Array[String]): Unit
  =
  {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    var h1=new compare
    h1.run
  }
}

