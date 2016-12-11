
package net.ladstatt.sierpinski

import android.content.Context
import android.graphics._
import android.util.{AttributeSet, SparseArray}
import android.view.{MotionEvent, View}

import scala.Option

/**
  * Implements a view which displays a sierpinski triangle.
  *
  * @param context
  * @param attrs
  */
class SierpinskiView(val context: Context, val attrs: AttributeSet) extends View(context, attrs) {

  var someWidth: Option[Int] = None

  var someSierpinski: Option[Sierpinski] = None

  val White = {
    val p = new Paint
    p.setColor(Color.WHITE)
    p
  }

  val Black = {
    val p = new Paint
    p.setColor(Color.BLACK)
    p
  }

  override protected def onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val currentWidth = getWidth

    someWidth match {
      case Some(width) if width == currentWidth => // do nothing
      case _ =>
        val w = Math.min(currentWidth, getHeight)
        someWidth = Option(w)
        val sierpinski: Sierpinski = Sierpinski(Triangle(Pos(0, 0), w), 7)
        someSierpinski = Option(sierpinski)
        drawSierpinski(canvas, sierpinski)
    }

  }


  def drawSierpinski(canvas: Canvas, sierpinski: Sierpinski): Unit = {

    def toPath(t: Triangle): Path = {
      val p = new Path()
      p.setFillType(Path.FillType.EVEN_ODD)
      p.moveTo(t.a.x, t.a.y)
      p.lineTo(t.b.x, t.b.y)
      p.lineTo(t.c.x, t.c.y)
      p.lineTo(t.a.x, t.a.y)
      p.close()
      p
    }

    for (t <- sierpinski.triangles) {
      val p: Paint = t.color match {
        case SColor.White => White
        case SColor.Black => Black
      }
      canvas.drawPath(toPath(t), p)
    }
  }

}
