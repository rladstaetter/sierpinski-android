package net.ladstatt.sierpinski

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

/**
  * Created by lad on 11/12/2016.
  */
class SierpinskiTest {

  val pos: Pos = Pos(0, 0)
  val t = Triangle(pos, 1)

  /** white color is defined with all three components set to 1 **/
  @Test def testWhiteColor(): Unit = assertEquals(SColor.White, SColor(1, 1, 1))

  /** black color is defined as having all components set to 0 **/
  @Test def testBlackColor(): Unit = assertEquals(SColor.Black, SColor(0, 0, 0))

  /**
    * tests a triangle with length = 1
    */
  @Test def basicTriangle(): Unit = {
    assertEquals(Pos(0, 0), t.a)
    assertEquals(Pos(1, 0), t.b)
    assertEquals(Pos(0.5f, (Math.sqrt(3) / 2).toFloat), t.c)
    assertEquals(SColor.White, t.color)
  }

  @Test def siblingsTest(): Unit = {
    assertEquals(List(
      Triangle(Pos(0.25f, 0.4330127f), Pos(0.75f, 0.4330127f), Pos(0.5f, 0.8660254f), SColor(1.0, 1.0, 1.0)),
      Triangle(Pos(0.0f, 0.0f), Pos(0.5f, 0.0f), Pos(0.25f, 0.4330127f), SColor(1.0, 1.0, 1.0)),
      Triangle(Pos(0.5f, 0.0f), Pos(1.0f, 0.0f), Pos(0.75f, 0.4330127f), SColor(1.0, 1.0, 1.0)))
      , t.siblings)
  }

  @Test def allSiblingsAreWhite(): Unit = assertTrue(t.siblings.forall(_.color == SColor.White))

  @Test def centerTriangleIsBlack(): Unit = assertTrue(t.centerT.color == SColor.Black)

  @Test def sierpinski0(): Unit = {
    assertEquals(Sierpinski(List(Triangle(Pos(0.0f, 0.0f), Pos(1.0f, 0.0f), Pos(0.5f, 0.8660254f), SColor(1.0, 1.0, 1.0)))), Sierpinski(Triangle(Pos(0, 0), 1), 0))
  }

  @Test def sierpinski1(): Unit = {
    assertEquals(
      Sierpinski(List(
        Triangle(Pos(0.0f, 0.0f), Pos(1.0f, 0.0f), Pos(0.5f, 0.8660254f), SColor(1.0, 1.0, 1.0)),
        Triangle(Pos(0.5f, 0.0f), Pos(0.75f, 0.4330127f), Pos(0.25f, 0.4330127f), SColor(0.0, 0.0, 0.0)),
        Triangle(Pos(0.25f, 0.4330127f), Pos(0.75f, 0.4330127f), Pos(0.5f, 0.8660254f), SColor(1.0, 1.0, 1.0)),
        Triangle(Pos(0.0f, 0.0f), Pos(0.5f, 0.0f), Pos(0.25f, 0.4330127f), SColor(1.0, 1.0, 1.0)),
        Triangle(Pos(0.5f, 0.0f), Pos(1.0f, 0.0f), Pos(0.75f, 0.4330127f), SColor(1.0, 1.0, 1.0)),
        Triangle(Pos(0.25f, 0.4330127f), Pos(0.75f, 0.4330127f), Pos(0.5f, 0.8660254f), SColor(1.0, 1.0, 1.0)),
        Triangle(Pos(0.0f, 0.0f), Pos(0.5f, 0.0f), Pos(0.25f, 0.4330127f), SColor(1.0, 1.0, 1.0)),
        Triangle(Pos(0.5f, 0.0f), Pos(1.0f, 0.0f), Pos(0.75f, 0.4330127f), SColor(1.0, 1.0, 1.0)))), Sierpinski(Triangle(Pos(0, 0), 1), 1))
  }

  @Test def sierpinski7(): Unit = {
    assertEquals(7652, Sierpinski(Triangle(Pos(0, 0), 1), 7).triangles.length)
  }
}
