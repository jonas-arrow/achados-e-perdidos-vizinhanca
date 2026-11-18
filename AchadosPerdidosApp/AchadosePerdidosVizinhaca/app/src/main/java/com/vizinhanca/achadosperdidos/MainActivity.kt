package com.vizinhanca.achadosperdidos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vizinhanca.achadosperdidos.adapter.PostAdapter
import com.vizinhanca.achadosperdidos.data.PostDao
import com.vizinhanca.achadosperdidos.model.PostItem

class MainActivity : AppCompatActivity() {

    private lateinit var dao: PostDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o banco de dados local
        dao = PostDao(this)

        // Associa a lista do XML com o código
        recyclerView = findViewById(R.id.recyclerViewPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inserir alguns dados de teste caso o banco esteja vazio
        inserirDadosDeTeste()

        // Carrega os dados na tela
        carregarLista()

        // Configura o clique do botão de "+ Novo Post"
        val fabNovoPost = findViewById<FloatingActionButton>(R.id.fabNovoPost)
        fabNovoPost.setOnClickListener {
            // Aqui o Intent faz o papel de "chamar" a nova tela de cadastro
            val intent = Intent(this, NovoPostActivity::class.java)
            startActivity(intent)
        }
    }

    // Este bloco recarrega a lista toda vez que você volta para esta tela
    override fun onResume() {
        super.onResume()
        carregarLista()

        // Unifica a cor do botão flutuante (FAB)
        val fabNovoPost = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabNovoPost)
        val context = fabNovoPost.context
        val corFAB = androidx.core.content.ContextCompat.getColor(context, R.color.primaryLightColor)
        fabNovoPost.backgroundTintList = android.content.res.ColorStateList.valueOf(corFAB)
    }

    private fun carregarLista() {
        // Busca os posts do banco offline
        val listaDePosts = dao.listarPosts()

        // Entrega a lista para o Adapter construir os cartões
        adapter = PostAdapter(listaDePosts)
        recyclerView.adapter = adapter
    }

    private fun inserirDadosDeTeste() {
        val listaAtual = dao.listarPosts()
        if (listaAtual.isEmpty()) {
            val post1 = PostItem(
                titulo = "Cachorro caramelo assustado",
                descricao = "Encontrado perto da praça principal, está com uma coleira azul sem nome.",
                status = "Achado",
                latitude = -22.8808,
                longitude = -43.3643,
                fotoCaminho = ""
            )
            val post2 = PostItem(
                titulo = "Chaves do carro (Honda)",
                descricao = "Perdi um chaveiro com a chave do carro e um controle de portão.",
                status = "Perdido",
                latitude = -22.8820,
                longitude = -43.3650,
                fotoCaminho = ""
            )

            dao.salvarPost(post1)
            dao.salvarPost(post2)
        }
    }
}