package com.vizinhanca.achadosperdidos

import com.vizinhanca.achadosperdidos.model.PostItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Diz ao Retrofit que queremos fazer um POST na rota /posts/ do servidor
    @POST("posts/")
    fun enviarPostParaNuvem(@Body post: PostItem): Call<ResponseBody>

    companion object {
        // O SEU ENDEREÇO IP CONFIGURADO AQUI
        private const val BASE_URL = "http://192.168.1.108:8000/"

        fun criar(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}