package com.example.githubuserapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapi.R
import com.example.githubuserapi.data.model.FollowItem
import com.example.githubuserapi.databinding.ItemRowFollowBinding

class FollowAdapter(private val listFollow: ArrayList<FollowItem>): RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowFollowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val follow: FollowItem = listFollow[position]

        holder.binding.apply {
            tvItemUsername.text = follow.username
            tvItemID.text = follow.id.toString()

            photoUser.loadImage(follow.avatarUrl)
        }
    }

    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(500, 500))
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
            .centerCrop()
            .circleCrop()
            .into(this)
    }

    override fun getItemCount(): Int = listFollow.size

    class ListViewHolder (var binding: ItemRowFollowBinding): RecyclerView.ViewHolder(binding.root)
}