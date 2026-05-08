package br.edu.ifsp.scl.sc3044254.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.sc3044254.fasttripplanner.databinding.ActivityHospedagemBinding

class HospedagemActivity : AppCompatActivity() {
    private lateinit var resumoActivityLauncher: ActivityResultLauncher<Intent>
    private val binding: ActivityHospedagemBinding by lazy {
        ActivityHospedagemBinding.inflate(layoutInflater)
    }
    private lateinit var dados: DadosViagem
    private var modoEconomico = false

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

        resumoActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.finish.setOnClickListener {
            val hospedagem = tipoHosp()
            val servicos = ArrayList<String>()
            var orcamentoDiario = dados.orcamento

            if (modoEconomico) {
                orcamentoDiario *= 0.85
            }

            if (binding.transporte.isChecked) servicos.add("Transporte")
            if (binding.alimentacao.isChecked) servicos.add("Alimentação")
            if (binding.passeios.isChecked) servicos.add("Passeios")

            if (hospedagem.isBlank()){
                Toast.makeText(this,
                    "Por favor, selecione o tipo de hospedagem", Toast.LENGTH_SHORT).show()
            } else {
                val resumoIntent = Intent(this, ResumoActivity::class.java)
                DadosViagem.inserirIntent(resumoIntent, dados.destino, dados.dias, orcamentoDiario,
                    hospedagem, servicos)

                resumoActivityLauncher.launch(resumoIntent)
            }

        }

        binding.back.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.modoEconomico.setOnClickListener {
            if(binding.modoEconomico.isChecked) ativarEconomia()
            else desativarEconomia()
        }
    }

    private fun tipoHosp (): String {
        val id = binding.hospedagem.checkedRadioButtonId
        if (id == R.id.conforto) return "Conforto"
        if (id == R.id.economica) return "Econômica"
        if (id == R.id.luxo) return "Luxo"
        return ""
    }

    private fun ativarEconomia () {
        binding.hospedagem.check(R.id.economica)

        binding.luxo.isClickable = false
        binding.conforto.isClickable = false
        binding.passeios.isClickable = false

        binding.passeios.isChecked = false
        modoEconomico = true
    }

    private fun desativarEconomia () {
        binding.luxo.isClickable = true
        binding.conforto.isClickable = true
        binding.passeios.isClickable = true
        modoEconomico = false
    }
}