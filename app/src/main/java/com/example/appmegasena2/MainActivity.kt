package com.example.appmegasena2

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.graphics.rotationMatrix
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.TODO as KotlinTODO

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.gerador)

        prefs = getSharedPreferences("bd", Context.MODE_PRIVATE)
        val result = prefs.getString("result", "Nenhum jogo salvo")
        txtResult.setTextColor(Color.BLACK)
        txtResult.text = "Última aposta:\n $result"

       // girar a imagem no sentido horário
        rotation()

        btnGenerate.setOnClickListener {

            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        // validando capos vazios

        if (text.isEmpty()) {
            rotation()
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }
        val qtd = text.toInt() // converte para inteiro
        //validar campo informado entre 6 e 15
        if (qtd < 6 || qtd > 15) {
            rotation()
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }


        val numbers = mutableSetOf<Int>()
        val random = Random
        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qtd) {
                break
            }
        }
        // parar o giro da imagem
        rotationEnd()

        txtResult.setTextColor(Color.BLACK)

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()
        zoom()


    }

    fun rotation() {
        val img: ImageView = findViewById(R.id.img)
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotation1)
        img.startAnimation(rotate)

    }

    fun rotationEnd() {
        val img: ImageView = findViewById(R.id.img)
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotation)
        img.startAnimation(rotate)

    }
    fun zoom() {
        val img: ImageView = findViewById(R.id.img)
        val rotate = AnimationUtils.loadAnimation(this, R.anim.zoom)
        img.startAnimation(rotate)

    }

}