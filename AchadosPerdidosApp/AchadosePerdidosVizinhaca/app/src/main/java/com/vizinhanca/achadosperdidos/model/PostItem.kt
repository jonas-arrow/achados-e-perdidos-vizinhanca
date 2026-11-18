package com.vizinhanca.achadosperdidos.model

class PostItem (
    var idLocal: Int = 0,               // ID interno do SQLite no celular
    var idServidor: String? = null,     // ID gerado pela API REST depois da sincronização
    var titulo: String,
    var descricao: String,
    var status: String,                 // "Achado" ou "Perdido"
    var latitude: Double,
    var longitude: Double,
    var fotoCaminho: String            // Caminho da foto no celular ou URL remota
)