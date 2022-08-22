package com.example.justeatsample.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatsample.databinding.DetailItemAdapterBinding

class DetailsPageAdapter : RecyclerView.Adapter<DetailsPageAdapter.ViewHolder>() {


    private val pairs = ArrayList<Pair<String, String>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DetailItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setPairs(list: ArrayList<Pair<String, String>>) {
        pairs.addAll(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.keyTv.text = pairs[position].first
        holder.binding.valueTv.text = pairs[position].second
        if (position == pairs.size - 1) {
            holder.binding.separator.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return pairs.size
    }

    class ViewHolder(val binding: DetailItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)
}