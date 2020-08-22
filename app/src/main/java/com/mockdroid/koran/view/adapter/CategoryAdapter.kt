package com.mockdroid.koran.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockdroid.koran.R
import com.mockdroid.koran.model.CategoryMenu
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(val data: List<CategoryMenu>?) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.image.setImageResource(data?.get(position)?.image ?: 0)
        holder.name.text = data?.get(position)?.name
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.img_category
        val name = itemView.tv_category
    }
}