package br.edu.ifsp.scl.sc3044254.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.sc3044254.fasttripplanner.databinding.ActivityHospedagemBinding
import br.edu.ifsp.scl.sc3044254.fasttripplanner.databinding.ActivityMainBinding

class HospedagemActivity : AppCompatActivity() {
    private lateinit var resumoActivityLauncher: ActivityResultLauncher<Intent>
    private val binding: ActivityHospedagemBinding by lazy {
        ActivityHospedagemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.hospedagem)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dados =  DadosViagem.lerIntent(intent)
        binding.titulo.text = dados.destino
    }
}