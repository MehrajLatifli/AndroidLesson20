package com.example.androidlesson20.views.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.androidlesson20.R
import com.example.androidlesson20.databinding.FragmentOnboardingBinding
import com.example.androidlesson20.models.onboarding.Onboard
import com.example.androidlesson20.views.adapters.OnboardAdapter

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private val onboardList = arrayListOf(
        Onboard(R.drawable.onboard_1, "Get The Freshest Fruit Salad Combo", "We deliver the best and freshest fruit salad in town. Order for a combo today!!!"),
        Onboard(R.drawable.onboard_2, "What is your firstname?", "")
    )

    private val onboardAdapter = OnboardAdapter()

    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.adapter = onboardAdapter
        onboardAdapter.updateList(onboardList)

        binding.viewPager2.isUserInputEnabled = false

        binding.buttonNext.setOnClickListener {
            currentPage++
            if (currentPage < onboardList.size) {
                binding.viewPager2.setCurrentItem(currentPage, true)


            } else {
                binding.viewPager2.currentItem =  binding.viewPager2.currentItem + 1
                onboardAdapter.setNextButtonClicked(true)


                if ( binding.viewPager2.currentItem == onboardList.size - 1) {
                    binding.buttonNext.text = "Start Ordering"
                }
            }
        }

        onboardAdapter.setFragment(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


