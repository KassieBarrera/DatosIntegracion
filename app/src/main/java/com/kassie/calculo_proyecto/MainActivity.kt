package com.kassie.calculo_proyecto

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var editTextFuncion: EditText
    private lateinit var editTextLimiteInferior: EditText
    private lateinit var editTextLimiteSuperior: EditText
    private lateinit var buttonCalcular: Button
    private lateinit var textViewResultado: TextView
    private lateinit var graficoView: GraficaFuncion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextFuncion = findViewById(R.id.editTextFuncion)
        editTextLimiteInferior = findViewById(R.id.inferior)
        editTextLimiteSuperior = findViewById(R.id.superior)
        buttonCalcular = findViewById(R.id.buttonCalcular)
        textViewResultado = findViewById(R.id.textViewResultado)
        graficoView = findViewById(R.id.graficoView)

        buttonCalcular.setOnClickListener {
            val funcion = editTextFuncion.text.toString()
            val limiteInferior = editTextLimiteInferior.text.toString().toDoubleOrNull()
            val limiteSuperior = editTextLimiteSuperior.text.toString().toDoubleOrNull()

            if (limiteInferior != null && limiteSuperior != null) {
                val resultado = calcularIntegral(funcion, limiteInferior, limiteSuperior)
                textViewResultado.text = "Resultado: $resultado"
                mostrarGrafico(funcion, limiteInferior, limiteSuperior)
            } else {
                textViewResultado.text = "Por favor, ingrese límites válidos."
            }
        }
    }

    private fun calcularIntegral(funcion: String, a: Double, b: Double): Double {
        val n = 10000 // número de segmentos
        val h = (b - a) / n
        var integral = 0.0

        for (i in 0 until n) {
            val x1 = a + i * h
            val x2 = a + (i + 1) * h
            integral += (evaluarFuncion(funcion, x1) + evaluarFuncion(funcion, x2)) * h / 2
        }
        return integral
    }

    private fun evaluarFuncion(funcion: String, x: Double): Double {
        return ExpressionBuilder(funcion)
            .variables("x")
            .build()
            .setVariable("x", x)
            .evaluate()
    }
    private fun mostrarGrafico(funcion: String, a: Double, b: Double) {
        val puntos = ArrayList<Pair<Float, Float>>()
        val n = 100
        val h = (b - a) / n
        val graficoViewHeight = graficoView.height.toFloat()
        val maxFunctionValue = 1000.0

        for (i in 0 until n + 1) {
            val x = a + i * h
            val y = evaluarFuncion(funcion, x)

            val normalizedY = graficoViewHeight - (y / maxFunctionValue * graficoViewHeight)
            puntos.add(Pair(x.toFloat(), normalizedY.toFloat()))

            //Puntos en plano
            Log.d("Grafico", "Punto: ($x, $y) -> Normalizado: ($x, $normalizedY)")
        }

        graficoView.setPuntos(puntos)
    }


}