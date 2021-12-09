package com.lolwalid.drawer.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.lolwalid.drawer.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

class FingerPaintView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint get() = initPaint()
    private val eraser by lazy { eraser() }
    private var color = 0xFF00FFFF.toInt()

    private val realBitmap by lazy { Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)  }
    private val tmpCanvas: Canvas by lazy { initCanvas() }
    private val paths = mutableListOf<Path>()

    private val currentPath: Path = Path()
    private var lastXPath: Float = 0F
    private var lastYPath: Float = 0F

    fun setColor(c: Int) {
        color = c
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (color == Color.TRANSPARENT) {
            tmpCanvas.drawPath(currentPath, eraser)
        } else {
            tmpCanvas.drawPath(currentPath, paint)
        }
        canvas?.apply {
            Logger.getGlobal().log(Level.WARNING, "${paths.size}")
            drawBitmap(realBitmap, 0F, 0F, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> touchStart(x, y)
                MotionEvent.ACTION_MOVE -> touchMove(x, y)
                MotionEvent.ACTION_UP -> touchUp()
            }
        }
        return true
    }

    private fun touchStart(x: Float, y: Float) {
        currentPath.reset()
        currentPath.moveTo(x, y)
        lastXPath = x
        lastYPath = y
    }

    private fun touchMove(x: Float, y: Float) {
        // TODO: Add tolerance ?
        // val dx = Math.abs(x - lastXPath);
        // val dy = Math.abs(y - lastYPath);

        currentPath.quadTo(lastXPath, lastYPath, (x + lastXPath) / 2, (y + lastYPath) / 2)
        lastXPath = x
        lastYPath = y
        invalidate()
    }

    private fun touchUp() {
        currentPath.lineTo(lastXPath, lastYPath)
        paths.add(currentPath)
    }

    private fun initPaint(): Paint {
        val p = Paint()
        p.isAntiAlias = true
        p.isDither = true
        p.color = color
        p.style = Paint.Style.STROKE
        p.strokeJoin = Paint.Join.ROUND
        p.strokeCap = Paint.Cap.ROUND
        p.strokeWidth = 8.0F
        return p
    }

    private fun eraser(): Paint {
        val p = Paint()
        p.isAntiAlias = true
        p.isDither = true
        p.style = Paint.Style.STROKE
        p.strokeWidth = 8.0F
        p.maskFilter = null
        p.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        return p
    }

    private fun initCanvas(): Canvas {
//        realBitmap.eraseColor(Color.TRANSPARENT)
        return Canvas(realBitmap)
    }
}