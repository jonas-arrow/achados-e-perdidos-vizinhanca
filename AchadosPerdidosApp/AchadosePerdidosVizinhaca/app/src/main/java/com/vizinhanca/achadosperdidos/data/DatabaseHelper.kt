package com.vizinhanca.achadosperdidos.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "AchadosPerdidos.db", null, 1) {

    // Este método roda automaticamente na primeira vez que o app for aberto
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE posts_cache (
                idLocal INTEGER PRIMARY KEY AUTOINCREMENT,
                idServidor TEXT,
                titulo TEXT,
                descricao TEXT,
                status TEXT,
                latitude REAL,
                longitude REAL,
                fotoCaminho TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    // Usado caso você precise atualizar a estrutura do banco no futuro
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS posts_cache")
        onCreate(db)
    }
}