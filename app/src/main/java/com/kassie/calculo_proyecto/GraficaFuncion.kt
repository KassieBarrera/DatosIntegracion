package com.kassie.calculo_proyecto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GraficaFuncion @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
    }

    private var puntos: List<Pair<Float, Float>> = emptyList()

    fun setPuntos(puntos: List<Pair<Float, Float>>) {
        this.puntos = puntos
        invalidate() // Llama a onDraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (puntos.isNotEmpty()) {
            for (i in 0 until puntos.size - 1) {
                val startX = puntos[i].first
                val startY = puntos[i].second
                val stopX = puntos[i + 1].first
                val stopY = puntos[i + 1].second
                canvas.drawLine(startX, startY, stopX, stopY, paint)
            }
        }
    }
}
