package com.potatomeme.custom_ui.chart.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val textPaint = Paint()
    private var dataPoints = listOf(10f, 20f, 30f, 25f, 15f)

    private val padding = 50f

    init {
        paint.color = Color.BLUE
        paint.strokeWidth = 5f
        paint.isAntiAlias = true

        textPaint.color = Color.BLACK
        textPaint.textSize = 30f
        textPaint.isAntiAlias = true
    }

    fun setData(data:List<Float>){
        dataPoints = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (dataPoints.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()
        val pointCount = dataPoints.size

        val xInterval = (width - 2 * padding) / (pointCount - 1)
        val yMax = dataPoints.maxOrNull() ?: 1f
        val yMin = dataPoints.minOrNull() ?: 0f
        val yRange = yMax - yMin

        for (i in 0 until pointCount - 1) {
            val startX = padding + i * xInterval
            val startY = height - padding - (dataPoints[i] - yMin) / yRange * (height - 2 * padding)
            val stopX = padding + (i + 1) * xInterval
            val stopY = height - padding - (dataPoints[i + 1] - yMin) / yRange * (height - 2 * padding)

            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }

        val baselineY = height - padding - (yMin - yMin) / yRange * (height - 2 * padding)
        paint.color = Color.RED
        canvas.drawLine(padding, baselineY, width - padding, baselineY, paint)
        paint.color = Color.BLUE

        for (i in dataPoints.indices) {
            val x = padding + i * xInterval
            val y = height - padding - (dataPoints[i] - yMin) / yRange * (height - 2 * padding)
            canvas.drawText(dataPoints[i].toString(), x, y - 10, textPaint)
        }
    }
}