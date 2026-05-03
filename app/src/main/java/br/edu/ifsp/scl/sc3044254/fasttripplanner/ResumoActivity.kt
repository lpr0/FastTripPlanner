package br.edu.ifsp.scl.sc3044254.fasttripplanner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import br.edu.ifsp.scl.sc3044254.fasttripplanner.databinding.ActivityHospedagemBinding
import br.edu.ifsp.scl.sc3044254.fasttripplanner.databinding.ActivityResumoBinding

class ResumoActivity : AppCompatActivity() {
    private val binding: ActivityResumoBinding by lazy {
        ActivityResumoBinding.inflate(layoutInflater)
    }
    private lateinit var dados: DadosViagem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dados =  DadosViagem.lerIntent(intent)

        binding.destino.text = dados.destino
        binding.valorTotal.text = "R$" + calctotal().toString()
        binding.totalDias.text = dados.dias.toString() + " dias"
        binding.tipoHospedagem.text = dados.tipoHospedagem

        if (dados.servicos.contains("Transporte")) {
            binding.l1.text = "Transporte"
            binding.l1.visibility = View.VISIBLE
        }
        if (dados.servicos.contains("Alimentação")) {
            binding.l2.text = "Alimentação"
            binding.l2.visibility = View.VISIBLE
        }
        if (dados.servicos.contains("Passeios")) {
            binding.l3.text = "Passeios"
            binding.l3.visibility = View.VISIBLE
        }

        binding.restart.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun calctotal (): Double {
        var tot = dados.dias.toDouble() * dados.orcamento
        if (dados.tipoHospedagem == "Conforto") tot = tot * 1.5
        if (dados.tipoHospedagem == "Luxo") tot = tot * 2.2

        if (dados.servicos.contains("Transporte")) tot = tot + 300.00
        if (dados.servicos.contains("Alimentação")) tot = tot + (50.0 * dados.dias.toDouble())
        if (dados.servicos.contains("Passeios")) tot = tot + (120.0 * dados.dias.toDouble())

        return tot
    }
}