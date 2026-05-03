package br.edu.ifsp.scl.sc3044254.fasttripplanner

import android.content.Intent
import android.os.Bundle
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
        ) {}

        binding.finish.setOnClickListener {
            val hospedagem = tipoHosp()
            val servicos = ArrayList<String>()

            if (binding.transporte.isActivated) servicos.add("Transporte")
            if (binding.alimentacao.isActivated) servicos.add("Alimentação")
            if (binding.passeios.isActivated) servicos.add("Passeios")

            if (hospedagem.isBlank()){
                Toast.makeText(this,
                    "Por favor, selecione o tipo de hospedagem", Toast.LENGTH_SHORT).show()
            } else {
                val resumoIntent = Intent(this, ResumoActivity::class.java)
                DadosViagem.inserirIntent(resumoIntent, dados.destino, dados.dias, dados.orcamento,
                    hospedagem, servicos)

                resumoActivityLauncher.launch(resumoIntent)
            }

        }

        binding.back.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun tipoHosp (): String {
        val id = binding.hospedagem.checkedRadioButtonId
        if (id == R.id.conforto) return "Conforto"
        if (id == R.id.economica) return "Econômica"
        if (id == R.id.luxo) return "Luxo"
        return ""
    }
}