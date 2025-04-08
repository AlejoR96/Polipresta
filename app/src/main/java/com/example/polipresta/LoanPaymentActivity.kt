package com.example.polipresta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoanPaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loan_payment)

        // Vincular elementos
        val rbPagoMinimo = findViewById<RadioButton>(R.id.rbPagoMinimo)
        val rbPagoTotal = findViewById<RadioButton>(R.id.rbPagoTotal)
        val btnPSE = findViewById<Button>(R.id.btnPSE)
        val btnTarjeta = findViewById<Button>(R.id.btnTarjeta)

        // Manejar clic en botones
        btnPSE.setOnClickListener {
            if (rbPagoMinimo.isChecked || rbPagoTotal.isChecked) {
                iniciarProcesoPago("PSE")
            } else {
                Toast.makeText(this, "Seleccione un tipo de pago", Toast.LENGTH_SHORT).show()
            }
        }

        btnTarjeta.setOnClickListener {
            if (rbPagoMinimo.isChecked || rbPagoTotal.isChecked) {
                iniciarProcesoPago("Tarjeta")
            } else {
                Toast.makeText(this, "Seleccione un tipo de pago", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarProcesoPago(metodo: String) {
        val monto = if (findViewById<RadioButton>(R.id.rbPagoMinimo).isChecked) {
            390000.0
        } else {
            5500000.0
        }

        // Navegar a otra actividad o lógica de pago
        val intent = Intent(this, LoanPaymentActivity::class.java).apply {
            putExtra("METODO", metodo)
            putExtra("MONTO", monto)
        }
        startActivity(intent)
    }
}