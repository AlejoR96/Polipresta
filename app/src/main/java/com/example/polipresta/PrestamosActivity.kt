package com.tuapp.polipresta

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.polipresta.R

class activity_prestamos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamos)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupPrestamos)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)

        btnConfirmar.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val radioButton = findViewById<RadioButton>(selectedId)
                val montoSeleccionado = radioButton.text.toString()
                Toast.makeText(this, "Seleccionaste $montoSeleccionado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor selecciona un monto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


