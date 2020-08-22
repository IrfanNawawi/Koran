package com.mockdroid.koran.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mockdroid.koran.R
import com.mockdroid.koran.model.ArticlesItem
import kotlinx.android.synthetic.main.item_top_headlines.view.*

class TopHeadlineAdapter(val data: List<ArticlesItem>?) :
    RecyclerView.Adapter<TopHeadlineAdapter.TopHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_headlines, parent, false)
        return TopHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: TopHolder, position: Int) {
        Glide.with(holder.itemView.context).load(data?.get(position)?.urlToImage).into(holder.image)
        holder.name.text = data?.get(position)?.source?.name
        holder.title.text = data?.get(position)?.title
    }

    class TopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.img_news
        val name = itemView.tv_name
        val title = itemView.tv_title
    }
}