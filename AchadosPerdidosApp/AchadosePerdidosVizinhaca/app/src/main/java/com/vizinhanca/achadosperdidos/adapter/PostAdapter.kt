package com.vizinhanca.achadosperdidos.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vizinhanca.achadosperdidos.R
import com.vizinhanca.achadosperdidos.model.PostItem

class PostAdapter(private val listaPosts: List<PostItem>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtStatus: TextView = itemView.findViewById(R.id.txtPostStatus)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtPostTitulo)
        val txtDescricao: TextView = itemView.findViewById(R.id.txtPostDescricao)
        // Adicionamos a captura da ImageView do XML do cartão
        val imgFoto: ImageView = itemView.findViewById(R.id.imgPostFoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = listaPosts[position]

        holder.txtStatus.text = post.status.uppercase()
        holder.txtTitulo.text = post.titulo
        holder.txtDescricao.text = post.descricao

        // --- Lógica da Imagem ---
        if (post.fotoCaminho.isNotEmpty()) {
            try {
                // Se existe um endereço de foto no banco, carrega a imagem real
                holder.imgFoto.setImageURI(Uri.parse(post.fotoCaminho))
            } catch (e: Exception) {
                // Se der erro (ex: usuário apagou a foto da galeria), coloca um ícone padrão
                holder.imgFoto.setImageResource(android.R.drawable.ic_menu_camera)
            }
        } else {
            // Os posts de teste antigos (que não tem foto) recebem o ícone padrão
            holder.imgFoto.setImageResource(android.R.drawable.ic_menu_camera)
        }
    }

    override fun getItemCount(): Int {
        return listaPosts.size
    }
}