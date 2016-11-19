package net.ladstatt.sierpinski

case class Pos(x: Float, y: Float)

object SColor {
  val White = SColor(1, 1, 1)
  val Black = SColor(0, 0, 0)
}

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

  private def center(l: Pos, r: Pos): Pos = Pos(l.x + (r.x - l.x) / 2, l.y + (r.y - l.y) / 2)

  lazy val cAB = center(a, b)
  lazy val cAC = center(a, c)
  lazy val cBC = center(b, c)
  lazy val centerT: Triangle = Triangle(cAB, cBC, cAC)
  lazy val topT: Triangle = Triangle(cAC, cBC, c)
  lazy val leftT: Triangle = Triangle(a, cAB, cAC)
  lazy val rightT: Triangle = Triangle(cAB, b, cBC)
  lazy val siblings: List[Triangle] = List(topT, leftT, rightT)

}

object Sierpinski {

  def mkSierpinski(t: Triangle, depth: Int): List[Triangle] = {
    depth match {
      case 0 => List[Triangle](t)
      case d =>
        val c: Triangle = t.centerT.copy(color = SColor.Black)
        List(t, c) ++ t.siblings ++ t.siblings.flatMap(mkSierpinski(_, d - 1))
    }
  }

}

