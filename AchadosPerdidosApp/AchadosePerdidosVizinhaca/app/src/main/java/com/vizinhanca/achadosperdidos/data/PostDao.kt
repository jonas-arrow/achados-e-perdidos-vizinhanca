package com.vizinhanca.achadosperdidos.data

import android.content.ContentValues
import android.content.Context
import com.vizinhanca.achadosperdidos.model.PostItem

class PostDao(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // Método para Salvar (Equivalente ao INSERT)
    fun salvarPost(post: PostItem): Long {
        val db = dbHelper.writableDatabase

        // O ContentValues "empacota" os dados para o SQLite entender
        val values = ContentValues().apply {
            put("idServidor", post.idServidor)
            put("titulo", post.titulo)
            put("descricao", post.descricao)
            put("status", post.status) // Achado ou Perdido
            put("latitude", post.latitude)
            put("longitude", post.longitude)
            put("fotoCaminho", post.fotoCaminho)
        }

        // Insere na tabela 'posts_cache' e retorna o ID da linha criada
        val id = db.insert("posts_cache", null, values)
        db.close()
        return id
    }

    // Método para Buscar (Equivalente ao SELECT)
    fun listarPosts(): List<PostItem> {
        val lista = mutableListOf<PostItem>()
        val db = dbHelper.readableDatabase

        // Faz a busca. O último parâmetro "idLocal DESC" traz os mais recentes primeiro
        val cursor = db.query("posts_cache", null, null, null, null, null, "idLocal DESC")

        // Percorre os resultados do banco e monta os objetos
        while (cursor.moveToNext()) {
            val post = PostItem(
                idLocal = cursor.getInt(cursor.getColumnIndexOrThrow("idLocal")),
                idServidor = cursor.getString(cursor.getColumnIndexOrThrow("idServidor")),
                titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao")),
                status = cursor.getString(cursor.getColumnIndexOrThrow("status")),
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                fotoCaminho = cursor.getString(cursor.getColumnIndexOrThrow("fotoCaminho"))
            )
            lista.add(post)
        }

        cursor.close()
        db.close()
        return lista
    }
}