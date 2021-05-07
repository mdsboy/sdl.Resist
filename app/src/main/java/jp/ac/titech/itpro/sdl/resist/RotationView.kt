package jp.ac.titech.itpro.sdl.resist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import jp.ac.titech.itpro.sdl.resist.RotationView

internal class RotationView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var direction = 0.0
    private val paint = Paint()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "onSizeChanged: w=$w h=$h")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width
        val h = height
        val cx = w / 2
        val cy = h / 2
        val r = Math.min(cx, cy) - 20
        paint.color = Color.LTGRAY
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), r.toFloat(), paint)
        paint.color = Color.BLUE
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), 20f, paint)
        paint.strokeWidth = 10f
        val x = (cx + r * Math.cos(direction)).toFloat()
        val y = (cy + r * Math.sin(direction)).toFloat()
        canvas.drawLine(cx.toFloat(), cy.toFloat(), x, y, paint)
    }

    fun setDirection(th: Double) {
        direction = th - Math.PI / 2
        this.rotation
        invalidate()
    }

    companion object {
        private val TAG = RotationView::class.java.simpleName
    }
}
