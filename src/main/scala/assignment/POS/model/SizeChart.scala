package assignment.POS.model

// Case class to hold measurements for sizing and determine recommended size
case class SizeChart(shoulder: Double, bust: Double, waist: Double, hip: Double) {
  // Method to recommend a size based on measurements
  def recommendSize: String = {
    (shoulder, bust, waist, hip) match {
      case (s, b, w, h) if s <= 35 && b <= 80 && w <= 60 && h <= 85 => "XS"
      case (s, b, w, h) if s <= 37 && b <= 85 && w <= 65 && h <= 90 => "S"
      case (s, b, w, h) if s <= 39 && b <= 90 && w <= 70 && h <= 95 => "M"
      case (s, b, w, h) if s <= 41 && b <= 95 && w <= 75 && h <= 100 => "L"
      case (s, b, w, h) if s > 41 || b > 95 || w > 75 || h > 100 => "XL"
      case _ => "Size Not Available"
    }
  }
}
// Companion object for SizeChart to provide an apply method for easy instantiation
object SizeChart {
  def apply(shoulder: Double, bust: Double, waist: Double, hip: Double): SizeChart = {
    new SizeChart(shoulder, bust, waist, hip)
  }
}