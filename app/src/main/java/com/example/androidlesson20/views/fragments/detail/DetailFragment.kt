package com.example.androidlesson20.views.fragments.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson20.databinding.FragmentDetailBinding
import com.example.androidlesson20.utilities.gone
import com.example.androidlesson20.utilities.loadImageWithoutBindingAdapter
import com.example.androidlesson20.utilities.visible
import com.example.androidlesson20.viewmodels.DetailViewModel
import com.example.androidlesson20.views.adapters.IngredientAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()
    private val args: DetailFragmentArgs by navArgs()

    private val ingredientAdapter = IngredientAdapter()


    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = args.id.toInt()


        viewModel.getRecipeById(productId)
        observeData()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.ingredientRecyclerView.layoutManager = horizontalLayoutManager

        val itemAnimator = DefaultItemAnimator()
        binding.ingredientRecyclerView.itemAnimator = itemAnimator

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.ingredientRecyclerView)

        binding.ingredientRecyclerView.adapter = ingredientAdapter

        startAutoScroll()
    }

    private fun startAutoScroll() {
        autoScrollRunnable = Runnable {
            val layoutManager = binding.ingredientRecyclerView.layoutManager as LinearLayoutManager
            var currentPosition = layoutManager.findFirstVisibleItemPosition()
            if (currentPosition == RecyclerView.NO_POSITION) {
                currentPosition = 0
            }
            val nextPosition = if (currentPosition == ingredientAdapter.itemCount - 1) 0 else currentPosition + 1
            binding.ingredientRecyclerView.smoothScrollToPosition(nextPosition)
            autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL)
        }
        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL)
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }





    companion object {
        private const val AUTO_SCROLL_INTERVAL = 3000L
    }


    private fun observeData() {
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                binding.textView1.text = it.name
                binding.textView2.text = "Prepare: ${it.prepTimeMinutes.toString() ?: ""} minutes"
                binding.textView3.text = "Cook: ${it.cookTimeMinutes.toString() ?: ""} minutes"
                binding.textView4.text = "Difficulty: ${it.difficulty.toString() ?: ""}"
                binding.textView5.text = "Cuisine: ${it.cuisine.toString() ?: ""}"
                binding.imageView.loadImageWithoutBindingAdapter(it.image)
                ingredientAdapter.updateList(it.ingredients ?: emptyList())
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBarContainer.progressBar2.visible()
                hideOtherWidgets()
            } else {
                binding.progressBarContainer.progressBar2.gone()
                showOtherWidgets()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hideOtherWidgets() {
        binding.detailConstraintLayout.gone()
    }

    private fun showOtherWidgets() {
        binding.detailConstraintLayout.visible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
