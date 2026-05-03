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
            val hospedagemIntent = Intent(this, HospedagemActivity::class.java)

            hospedagemIntent.putExtra("destino", binding.destino.text.toString())
            hospedagemIntent.putExtra("dias", binding.dias.text.toString())
            hospedagemIntent.putExtra("orcamento", binding.orcamento.text.toString())

            hospedagemActivityLauncher.launch(hospedagemIntent)
        }
    }
}