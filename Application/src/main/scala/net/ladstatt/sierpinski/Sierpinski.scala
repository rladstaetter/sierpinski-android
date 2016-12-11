package net.ladstatt.sierpinski

case class Pos(x: Float, y: Float) {
  def center(other: Pos): Pos = Pos(x + (other.x - x) / 2, y + (other.y - y) / 2)
}

object SColor {
  val White = SColor(1, 1, 1)
  val Black = SColor(0, 0, 0)
}

/**
  * Represents a RGB color, each component must be between 0 and 1 (max).
  *
  * @param r red color
  * @param g green color
  * @param b blue color
  */
case class SColor(r: Double, g: Double, b: Double) {
  require(0 <= r && r <= 1)
  require(0 <= g && g <= 1)
  require(0 <= b && b <= 1)
}

object Triangle {

  val yFactor: Float = (Math.sqrt(3) / 2).toFloat

  def apply(apos: Pos, length: Float): Triangle = {
    Triangle(apos, Pos(apos.x + length, apos.y), Pos(apos.x + length / 2, apos.y + yFactor * length))
  }

}

case class Triangle(a: Pos
                    , b: Pos
                    , c: Pos
                    , color: SColor = SColor.White) {

  private lazy val cAB = a.center(b)
  private lazy val cAC = a.center(c)
  private lazy val cBC = b.center(c)

  lazy val centerT: Triangle = Triangle(cAB, cBC, cAC, SColor.Black)
  private lazy val topT: Triangle = Triangle(cAC, cBC, c)
  private lazy val leftT: Triangle = Triangle(a, cAB, cAC)
  private lazy val rightT: Triangle = Triangle(cAB, b, cBC)
  lazy val siblings: List[Triangle] = List(topT, leftT, rightT)

}

case class Sierpinski(triangles: List[Triangle])

object Sierpinski {

  def apply(t: Triangle, depth: Int): Sierpinski = Sierpinski(mkTriangles(t, depth))

  def mkTriangles(t: Triangle, depth: Int): List[Triangle] = {
    depth match {
      case 0 => List[Triangle](t)
      case d => List(t, t.centerT) ++ t.siblings ++ t.siblings.flatMap(mkTriangles(_, d - 1))
    }
  }

}

