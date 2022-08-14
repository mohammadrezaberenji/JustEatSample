package com.example.justeatsample.ui.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.justeatsample.R
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.remote.apiModel.RestaurantResp
import com.example.justeatsample.databinding.MenuItemAdapterBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var list = ArrayList<Restaurant>()

    private val TAG = MenuAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MenuItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(list[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(true)
            .into(object : CustomTarget<Drawable?>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    Log.d(TAG, "onResourceReady: ")
                    val width =
                        holder.itemView.context.getResources().getDisplayMetrics().widthPixels;
                    val aspectRation = resource.intrinsicHeight
                        .toFloat() / resource.intrinsicWidth.toFloat()

                    val generatedImageHeight = Math.round(width * aspectRation)

                    val layoutParams = ConstraintLayout.LayoutParams(
                        width, generatedImageHeight
                    )

                    holder.binding.menuIv.layoutParams = layoutParams
                    holder.binding.menuIv.requestLayout()
                    holder.binding.menuIv.setImageDrawable(resource)
                }
            })

        holder.binding.nameTv.text = list[position].name
        holder.binding.statusTv.text = list[position].status
        holder.binding.ratingBar.rating = list[position].sortingValues.ratingAverage.toFloat()
        when (list[position].status) {
            Restaurant.Status.OPEN.status, Restaurant.Status.ORDER_AHEAD.status -> {
                holder.binding.statusTv.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.green
                    )
                )


            }
            Restaurant.Status.CLOSED.status -> {
                holder.binding.statusTv.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red_800
                    )
                )

            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(arrayList: ArrayList<Restaurant>) {
        list = arrayList

    }

    class ViewHolder(val binding: MenuItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)
}