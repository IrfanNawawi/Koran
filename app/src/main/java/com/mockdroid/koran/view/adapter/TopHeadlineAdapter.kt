package com.mockdroid.koran.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mockdroid.koran.R
import com.mockdroid.koran.model.ArticlesItem
import com.mockdroid.koran.utils.DateFormat
import kotlinx.android.synthetic.main.item_top_headlines.view.*

class TopHeadlineAdapter(
    private val data: List<ArticlesItem>?,
    private val itemClick: DetailClickListener
) :
    RecyclerView.Adapter<TopHeadlineAdapter.TopHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_headlines, parent, false)

        return TopHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: TopHolder, position: Int) {
        val item = data?.get(position)

        Glide.with(holder.itemView.context)
            .load(item?.urlToImage)
            .into(holder.image)
        holder.title.text = item?.title
        holder.desc.text = item?.description
        holder.author.text = item?.source?.name
        holder.date.text = DateFormat.getShortDate(item?.publishedAt)

        holder.cardItem.setOnClickListener {
            itemClick.detailData(item)
        }
    }

    inner class TopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.img_news
        val title = itemView.tv_title
        val desc = itemView.tv_desc
        val author = itemView.tv_author
        val date = itemView.tv_date
        val cardItem = itemView.cv_top_headline
    }

    interface DetailClickListener {
        fun detailData(item: ArticlesItem?)
    }
}