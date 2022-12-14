package com.example.justeatsample.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.justeatsample.R
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.databinding.MenuItemAdapterBinding
import kotlin.math.roundToInt

class MenuAdapter(
    private val onLickClick: (position: Int) -> Unit,
    private val onMenuItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {




    private val diffCallback = object : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(
            oldItem: Restaurant,
            newItem: Restaurant
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Restaurant,
            newItem: Restaurant
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

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
            .load(differ.currentList[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(true)
            .into(object : CustomTarget<Drawable?>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    val width =
                        holder.itemView.context.resources.displayMetrics.widthPixels;
                    val aspectRation = resource.intrinsicHeight
                        .toFloat() / resource.intrinsicWidth.toFloat()

                    val generatedImageHeight = (width * aspectRation).roundToInt()

                    val layoutParams = ConstraintLayout.LayoutParams(
                        width, generatedImageHeight
                    )

                    holder.binding.menuIv.layoutParams = layoutParams
                    holder.binding.menuIv.requestLayout()
                    holder.binding.menuIv.setImageDrawable(resource)
                }
            })

        if (differ.currentList[position].isFavorite) {
            holder.binding.likeBtn.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.liked_heart
                )
            )
            holder.binding.likeBtn.imageTintList =
                ContextCompat.getColorStateList(holder.itemView.context, R.color.red_800)
        } else {
            holder.binding.likeBtn.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.heart_1_
                )
            )
            holder.binding.likeBtn.imageTintList =
                ContextCompat.getColorStateList(holder.itemView.context, R.color.text_color)
        }

        holder.binding.distanceTv.text =
            differ.currentList[position].sortingValues.distance.toString().plus(" ")
                .plus(holder.itemView.context.getString(R.string.mile))
        holder.binding.nameTv.text = differ.currentList[position].name
        holder.binding.statusTv.text = differ.currentList[position].status
        holder.binding.ratingBar.rating =
            differ.currentList[position].sortingValues.ratingAverage.toFloat()
        holder.binding.likeBtn.setOnClickListener {
            onLickClick.invoke(position)
        }
        holder.binding.cardItem.setOnClickListener {
            onMenuItemClick.invoke(position)
        }
        when (differ.currentList[position].status) {
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
        return differ.currentList.size
    }


    fun setList(arrayList: ArrayList<Restaurant>) {
        differ.submitList(arrayList)
    }

    fun likeItem(position: Int) {
        notifyItemChanged(position)
    }

    class ViewHolder(val binding: MenuItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)


}