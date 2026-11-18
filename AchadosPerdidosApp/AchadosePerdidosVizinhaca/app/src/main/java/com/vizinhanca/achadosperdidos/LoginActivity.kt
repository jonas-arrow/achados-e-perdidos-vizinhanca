package com.vizinhanca.achadosperdidos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica a "memória" do celular para ver se o usuário já fez login antes
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val jaLogado = prefs.getBoolean("isLogged", false)

        if (jaLogado) {
            // Se ele já estiver logado, não mostra a tela de login. Pula direto para a lista!
            abrirTelaPrincipal()
            return
        }

        // Se não estiver logado, carrega o visual da tela de login
        setContentView(R.layout.activity_login)

        val editNome = findViewById<TextInputEditText>(R.id.editNome)
        val editSenha = findViewById<TextInputEditText>(R.id.editSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val nome = editNome.text.toString()
            val senha = editSenha.text.toString()

            if (nome.isNotEmpty() && senha.isNotEmpty()) {
                // Grava na memória que o usuário agora está logado
                prefs.edit()
                    .putBoolean("isLogged", true)
                    .putString("userName", nome)
                    .apply()

                Toast.makeText(this, "Bem-vindo(a), $nome!", Toast.LENGTH_SHORT).show()
                abrirTelaPrincipal()
            } else {
                Toast.makeText(this, "Preencha seu nome e senha para entrar!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun abrirTelaPrincipal() {
        // O Intent transporta o usuário da tela de Login para a tela da Lista Principal
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // O comando finish() destrói a tela de login.
        // Assim, se o usuário apertar o botão de "Voltar" no celular, o app fecha em vez de voltar pro login.
        finish()
    }
}