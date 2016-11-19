
package net.ladstatt.sierpinski

import android.content.Context
import android.graphics._
import android.util.{AttributeSet, SparseArray}
import android.view.{MotionEvent, View}


class SierpinskiView(val context: Context, val attrs: AttributeSet) extends View(context, attrs) {

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

    val height: Int = getHeight
    val width: Int = getWidth

    val triangles: List[Triangle] = Sierpinski.mkSierpinski(Triangle(Pos(0, 0), width.toFloat), 7)

    for (t <- triangles) {
      val p: Paint = t.color match {
        case SColor.White => White
        case SColor.Black => Black
      }
      canvas.drawPath(toPath(t), p)
    }
  }

  def convert(c: SColor): Color = {
    new Color()
  }

  def asArray(t: Triangle): Array[Float] = {
    Array(t.a.x, t.a.y, t.b.x, t.b.y,
      t.b.x, t.b.y, t.c.x, t.c.y,
      t.c.x, t.c.y, t.a.x, t.a.y)
  }

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
}
