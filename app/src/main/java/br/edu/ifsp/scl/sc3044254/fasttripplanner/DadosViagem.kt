package br.edu.ifsp.scl.sc3044254.fasttripplanner

import android.content.Intent

data class DadosViagem(
    val destino: String,
    val dias: Int,
    val orcamento: Double,
    val tipoHospedagem: String,
    val servicos: ArrayList<String>
) {
    companion object util {
        fun lerIntent (intent: Intent): DadosViagem {
            return DadosViagem(
                intent.getStringExtra("destino")?:"",
                intent.getIntExtra("dias", 1),
                intent.getDoubleExtra("orcamento", 1.00),
                intent.getStringExtra("tipoHospedagem")?:"",
                intent.getStringArrayListExtra("servicos")?: ArrayList<String>()
            )
        }
        fun inserirIntent(
            intent: Intent,
            destino: String,
            dias: Int,
            orcamento: Double,
            tipoHospedagem: String?,
            servicos: ArrayList<String>?
        ) {
            intent.putExtra("destino", destino)
            intent.putExtra("dias", dias)
            intent.putExtra("orcamento", orcamento)
            intent.putExtra("tipoHospedagem", tipoHospedagem)
            intent.putExtra("servicos", servicos)
        }
        fun inserirIntent (intent: Intent, dados: DadosViagem) {
            inserirIntent(intent, dados.destino, dados.dias, dados.orcamento, dados.tipoHospedagem, dados.servicos)
        }
    }
}
