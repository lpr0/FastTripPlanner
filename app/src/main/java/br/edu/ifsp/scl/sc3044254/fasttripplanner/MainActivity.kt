package br.edu.ifsp.scl.sc3044254.fasttripplanner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.sc3044254.fasttripplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var hospedagemActivityLauncher: ActivityResultLauncher<Intent>
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.next.setOnClickListener { view ->
            val destino = binding.destino.text.toString()
            val dias = binding.dias.text.toString()
            val orcamento = binding.orcamento.text.toString()

            if (validarDados(destino, dias, orcamento)) {
                val hospedagemIntent = Intent(this, HospedagemActivity::class.java)

                hospedagemIntent.putExtra("destino",destino )
                hospedagemIntent.putExtra("dias", dias)
                hospedagemIntent.putExtra("orcamento", orcamento)

                hospedagemActivityLauncher.launch(hospedagemIntent)
            }
        }
    }

    private fun validarDados (destino: String, dias: String, orcamento: String): Boolean {
        if (destino.isBlank() || dias.isBlank() || orcamento.isBlank()) {
            Toast.makeText(this,
                "Prencha todos os campos para continuar.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (dias == "0") {
            Toast.makeText(this,
                "Informe uma quantidade de dias válida", Toast.LENGTH_SHORT).show()
            return false
        }
        if (orcamento == "0") {
            Toast.makeText(this,
                "Informe um orçamento válido.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

