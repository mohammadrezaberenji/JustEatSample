package com.example.justeatsample.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatsample.data.source.local_models.SortValueItem
import com.example.justeatsample.databinding.SortValueItemAdapterBinding

class SortValuesAdapter(
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<SortValuesAdapter.ViewHolder>() {



    private val diffCallback = object : DiffUtil.ItemCallback<SortValueItem>() {
        override fun areItemsTheSame(
            oldItem: SortValueItem,
            newItem: SortValueItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SortValueItem,
            newItem: SortValueItem
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SortValueItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.binding.sortValueTv.text = differ.currentList[position].name
        holder.binding.radioBtn.isChecked = differ.currentList[position].isSelected
        holder.binding.cardView.setOnClickListener {
            onItemClick.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setList(arrayList: ArrayList<SortValueItem>) {
        differ.submitList(arrayList)
    }


    class ViewHolder(val binding: SortValueItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)


}