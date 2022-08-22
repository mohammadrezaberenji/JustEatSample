package com.example.justeatsample.ui.fragments.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.justeatsample.R
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.databinding.FragmentDetailsPageBinding
import com.example.justeatsample.ui.adapters.DetailsPageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsPageFragment : Fragment() {

    private val TAG = DetailsPageFragment::class.java.simpleName
    private var _binding: FragmentDetailsPageBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsPageFragmentArgs by navArgs()
    var pairs = ArrayList<Pair<String, String>>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsPageBinding.inflate(
            LayoutInflater.from(inflater.context),
            container,
            false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgumentsAndPassToViewModel()
        setUpToolBar()
        loadImage()
        setUpView()
    }

    private fun getArgumentsAndPassToViewModel() {
        Log.i(TAG, "getArgumentsAndPassToViewModel: ${args.restaurant} ")
        makePairsOfModel(args.restaurant)
    }

    private fun setUpToolBar() {
        binding.toolbar.backIv.isVisible = true
        binding.toolbar.titleTv.text = args.restaurant.name
        binding.toolbar.backIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setUpView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DetailsPageAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setPairs(pairs)

    }

    private fun makePairsOfModel(model: Restaurant) {

        pairs.add(
            Pair(
                requireContext().getString(R.string.favorite_state),
                if (model.isFavorite) getString(R.string.is_fav) else getString(R.string.not_fave)
            )
        )
        pairs.add(Pair(requireContext().getString(R.string.name), model.name))
        pairs.add(Pair(requireContext().getString(R.string.status), model.status))
        pairs.add(
            Pair(
                requireContext().getString(R.string.average_product_price),
                model.sortingValues.averageProductPrice.toString()
            )
        )
        pairs.add(
            Pair(
                requireContext().getString(R.string.best_match),
                model.sortingValues.bestMatch.toString()
            )
        )
        pairs.add(
            Pair(
                requireContext().getString(R.string.delivery_costs),
                model.sortingValues.deliveryCosts.toString()
            )
        )
        pairs.add(
            Pair(
                requireContext().getString(R.string.distance),
                model.sortingValues.distance.toString()
            )
        )
        pairs.add(
            Pair(
                requireContext().getString(R.string.min_cost),
                model.sortingValues.minCost.toString()
            )
        )
        pairs.add(
            Pair(
                requireContext().getString(R.string.popularity),
                model.sortingValues.popularity.toString()
            )
        )
        pairs.add(
            Pair(
                requireContext().getString(R.string.rating_average),
                model.sortingValues.ratingAverage.toString()
            )
        )
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun loadImage() {
        Glide.with(requireContext())
            .load(args.restaurant.imageUrl)
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
                        requireContext().getResources().getDisplayMetrics().widthPixels;
                    val aspectRation = resource.intrinsicHeight
                        .toFloat() / resource.intrinsicWidth.toFloat()

                    val generatedImageHeight = Math.round(width * aspectRation)

                    val layoutParams = ConstraintLayout.LayoutParams(
                        width, generatedImageHeight
                    )

                    binding.imageIv.layoutParams = layoutParams
                    binding.imageIv.requestLayout()
                    binding.imageIv.setImageDrawable(resource)
                }
            })
    }
}