package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import net.objecthunter.exp4j.ExpressionBuilder

class HesapMakinesi : AppCompatActivity() {

    private lateinit var sonuc: TextView
    private lateinit var yapilanIslem: TextView

    private var ifade = ""
    private var parantezAcik = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hesap_makinesi)

        sonuc = findViewById(R.id.sonuc)
        yapilanIslem = findViewById(R.id.yapilanislem)

        // Sayı butonları
        val sayiButonlari = listOf(
            R.id.sifir, R.id.bir, R.id.iki, R.id.uc,
            R.id.dort, R.id.bes, R.id.alti,
            R.id.yedi, R.id.sekiz, R.id.dokuz
        )

        for (id in sayiButonlari) {
            findViewById<Button>(id).setOnClickListener {
                ekle((it as Button).text.toString())
            }
        }

        // Operatörler
        findViewById<Button>(R.id.arti).setOnClickListener { ekle("+") }
        findViewById<Button>(R.id.eksi).setOnClickListener { ekle("-") }
        findViewById<Button>(R.id.carpma).setOnClickListener { ekle("*") }
        findViewById<Button>(R.id.bolme).setOnClickListener { ekle("/") }
        findViewById<Button>(R.id.nokta).setOnClickListener { ekle(".") }

        // Parantez
        findViewById<Button>(R.id.parantez).setOnClickListener {
            if (!parantezAcik) {
                ekle("(")
                parantezAcik = true
            } else {
                ekle(")")
                parantezAcik = false
            }
        }

        // AC
        findViewById<Button>(R.id.ac).setOnClickListener {
            ifade = ""
            sonuc.text = "0"
            yapilanIslem.text = ""
            parantezAcik = false
        }

        // DEL
        findViewById<Button>(R.id.silme).setOnClickListener {
            if (ifade.isNotEmpty()) {
                ifade = ifade.dropLast(1)
                yapilanIslem.text = ifade
            }
        }

        // =
        findViewById<Button>(R.id.esit).setOnClickListener {
            hesapla()
        }
    }

    private fun ekle(deger: String) {
        ifade += deger
        yapilanIslem.text = ifade
    }

    private fun hesapla() {
        try {
            val sonucDegeri = ExpressionBuilder(ifade).build().evaluate()
            sonuc.text = sonucDegeri.toString()
            ifade = sonucDegeri.toString()
        } catch (e: Exception) {
            sonuc.text = "Hata"
            ifade = ""
        }
    }
}
