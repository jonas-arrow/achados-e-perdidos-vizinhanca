package com.vizinhanca.achadosperdidos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.vizinhanca.achadosperdidos.data.PostDao
import com.vizinhanca.achadosperdidos.model.PostItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovoPostActivity : AppCompatActivity() {

    private var caminhoDaFoto: String = ""
    private lateinit var imgPreview: ImageView

    private val abrirGaleria = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        if (uri != null) {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            caminhoDaFoto = uri.toString()
            imgPreview.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_post)

        val editTitulo = findViewById<TextInputEditText>(R.id.editTitulo)
        val editDescricao = findViewById<TextInputEditText>(R.id.editDescricao)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupStatus)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarPost)
        imgPreview = findViewById(R.id.imgPreviewFoto)
        val btnEscolherFoto = findViewById<Button>(R.id.btnEscolherFoto)

        val dao = PostDao(this)

        btnEscolherFoto.setOnClickListener {
            abrirGaleria.launch(arrayOf("image/*"))
        }

        btnSalvar.setOnClickListener {
            val tituloDigitado = editTitulo.text?.toString() ?: ""
            val descricaoDigitada = editDescricao.text?.toString() ?: ""
            val idRadioSelecionado = radioGroup.checkedRadioButtonId
            val radioSelecionado = findViewById<RadioButton>(idRadioSelecionado)
            val statusEscolhido = radioSelecionado.text.toString()

            if (tituloDigitado.isEmpty() || descricaoDigitada.isEmpty()) {
                Toast.makeText(this, "Preencha o título e a descrição!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novoPost = PostItem(
                titulo = tituloDigitado,
                descricao = descricaoDigitada,
                status = statusEscolhido,
                latitude = 0.0,
                longitude = 0.0,
                fotoCaminho = caminhoDaFoto
            )

            dao.salvarPost(novoPost)

            Toast.makeText(this, "A enviar para o servidor...", Toast.LENGTH_SHORT).show()

            ApiService.criar().enviarPostParaNuvem(novoPost).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@NovoPostActivity, "Sucesso! Salvo na nuvem e no celular.", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // ESTA LINHA FOI ALTERADA PARA MOSTRAR O ERRO REAL NA TELA DO TELEMÓVEL
                    Toast.makeText(this@NovoPostActivity, "Erro Técnico: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                    finish()
                }
            })
        }
    }
}
