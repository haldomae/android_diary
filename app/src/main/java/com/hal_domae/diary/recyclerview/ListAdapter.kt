package com.hal_domae.diary.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hal_domae.diary.R

// RecyclerView内のアイテム更新などを行う
class ListAdapter(private val data: MutableList<Map<String, String>>) : RecyclerView.Adapter<ViewHolder>(){
    // ViewHolderを作成している
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // ViewHolderの中身を変更
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDate.text = data[position]["date"]
        holder.itemText.text = data[position]["text"]
    }
}