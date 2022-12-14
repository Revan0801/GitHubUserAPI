package com.example.githubuserapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.data.model.SearchItem
import com.example.githubuserapi.databinding.ItemRowUserBinding

class SearchAdapter(private val listUser: ArrayList<SearchItem>): RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user: SearchItem = listUser[position]

        holder.binding.apply {
            tvItemUsername.text = user.username
            tvItemID.text = user.id.toString()

            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(holder.binding.photoUser)
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SearchItem)
    }

    class ListViewHolder (var binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root)
}