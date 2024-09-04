package com.potatomeme.custom_ui.sample.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.potatomeme.custom_ui.sample.R

class DrawBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : View(context, attrs, defStyle) {

    private var _currentDrawColor: Int = Color.BLACK
    val currentDrawColor: Int
        get() = _currentDrawColor

    private var _dotSize: Float = 1f
    val dotSize: Float
        get() = _dotSize

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private var currentPath = PathTool(_currentDrawColor, _dotSize)
    private val paths: MutableList<PathTool> = mutableListOf()

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DrawBoard, defStyle, 0).apply {
            try {
                _currentDrawColor = getColor(R.styleable.DrawBoard_dotColor, _currentDrawColor)
                _dotSize = getDimension(R.styleable.DrawBoard_dotSize, _dotSize)
            } finally {
                recycle()
            }
        }
        currentPath.color = currentDrawColor
        currentPath.dotSize = dotSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paths.forEach { pathTool ->
            paint.color = pathTool.color
            paint.strokeWidth = pathTool.dotSize
            canvas.drawPath(pathTool, paint)
        }

        if (!currentPath.isEmpty) {
            paint.color = currentPath.color
            paint.strokeWidth = currentPath.dotSize
            canvas.drawPath(currentPath, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
                invalidate()  // 필요할 때만 invalidate 호출
            }
            MotionEvent.ACTION_UP -> {
                paths.add(currentPath)
                currentPath = PathTool(_currentDrawColor, _dotSize)
                invalidate()  // 필요할 때만 invalidate 호출
            }
        }
        return true
    }
}

data class PathTool(
    var color: Int = 0,
    var dotSize: Float = 0f
) : Path()