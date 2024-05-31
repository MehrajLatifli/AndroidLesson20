package com.example.androidlesson20.views.fragments.home

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlesson20.R
import com.example.androidlesson20.databinding.FragmentHomeBinding
import com.example.androidlesson20.databinding.FragmentOnboardingBinding
import com.example.androidlesson20.models.onboarding.Onboard
import com.example.androidlesson20.utilities.gone
import com.example.androidlesson20.utilities.visible
import com.example.androidlesson20.viewmodels.HomeViewModel
import com.example.androidlesson20.views.adapters.OnboardAdapter
import com.example.androidlesson20.views.adapters.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.widget.TextView
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidlesson20.views.fragments.detail.DetailFragmentArgs


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var recipeAdapter = RecipeAdapter()

    private val viewModel by viewModels<HomeViewModel>()

    private val args: HomeFragmentArgs by navArgs()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeData()
        viewModel.getAllRecipes()

        val greeting = "Hello ${args.name}, What fruit salad combo do you want today?"
        binding.textView1.text = highlightName(greeting, args.name)

        binding.editText.addTextChangedListener { text ->
            val searchText = text.toString().trim()
            viewModel.searchRecipes(searchText)

            if(searchText.isNotBlank() && searchText.isNotEmpty())
            {
                binding.editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                binding.editText.compoundDrawables[0].setTint(ContextCompat.getColor(requireContext(), R.color.BlazingOrange))
            }
            else
            {
                binding.editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                binding.editText.compoundDrawables[0].setTint(ContextCompat.getColor(requireContext(), R.color.GrapeCreme))
            }

        }


        recipeAdapter.onClickItem = { itemId ->

            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(itemId.toInt())
            findNavController().navigate(action)
        }



    }



    private fun setUpRecyclerView() {


        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recycleViewHome.layoutManager = gridLayoutManager
        binding.recycleViewHome.adapter = recipeAdapter



    }

    private fun observeData() {
        viewModel.recipes.observe(viewLifecycleOwner) { products ->
            products?.let {
                recipeAdapter.updateList(it)
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
                Log.e("responseError", it)
            }
        }
    }

    private fun highlightName(text: String, name: String): SpannableString {
        val spannableString = SpannableString(text)
        val start = text.indexOf(name)
        if (start != -1) {
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.BlazingOrange)),
                start,
                start + name.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    private fun hideOtherWidgets() {


        binding.recycleViewHome.gone()

    }

    private fun showOtherWidgets() {

        binding.recycleViewHome.visible()

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}