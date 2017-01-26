import org.opencv.core.{Mat, Point, Scalar, Size}
import org.opencv.imgcodecs.Imgcodecs

/**
  * Created by cris on 26/01/17.
  */
class Hough {
  //hough circle transformation
  var srcCoins = new Mat()
  var srcCoinsGray = new Mat()

  srcCoins = org.opencv.imgcodecs.Imgcodecs.imread("/home/cris/mrcrstnherediagmez@gmail.com/AiDerma/images/coins.jpg")
  org.opencv.imgproc.Imgproc.cvtColor(srcCoins, srcCoinsGray, org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY)
  //reduce the noise
  org.opencv.imgproc.Imgproc.GaussianBlur(srcCoinsGray, srcCoinsGray, new Size(9, 9), 2, 2)
  //Imgcodecs.imwrite("blurred.png",srcCoinsGray)
  var circles = new Mat()
  org.opencv.imgproc.Imgproc.HoughCircles(srcCoinsGray, circles, org.opencv.imgproc.Imgproc.CV_HOUGH_GRADIENT, 1, srcCoinsGray.rows() / 8, 200, 100, 0, 0)

  var x: Int = 0
  /// Draw the circles detected
  for (x <- 0 until circles.cols()) {
    var vCircle = circles.get(0, x)
    var center: Point = new Point(Math.round(vCircle(0)), Math.round(vCircle(1)))
    var ratio = Math.round(vCircle(2)).toInt
    //draw the found circle
    org.opencv.imgproc.Imgproc.circle(srcCoins, center, ratio, new Scalar(0, 255, 255), 3)

  }
  Imgcodecs.imwrite("HoughCoins.png", srcCoins)

}
