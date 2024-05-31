package com.example.androidlesson20.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson20.databinding.ItemOnboardsBinding
import com.example.androidlesson20.models.onboarding.Onboard
import com.example.androidlesson20.utilities.gone
import com.example.androidlesson20.utilities.visible
import com.example.androidlesson20.views.fragments.onboarding.OnboardingFragmentDirections

class OnboardAdapter : RecyclerView.Adapter<OnboardAdapter.OnboardViewHolder>() {

    private var itemList = arrayListOf<Onboard>()
    private var isNextButtonClicked = false
    private var fragment: Fragment? = null

    inner class OnboardViewHolder(val itemBinding: ItemOnboardsBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardViewHolder {
        val view = ItemOnboardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: OnboardViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemBinding.item = item

        if (isNextButtonClicked) {
            holder.itemBinding.textView2.gone()
            holder.itemBinding.editText.visible()
        } else {
            holder.itemBinding.textView2.visible()
            holder.itemBinding.editText.gone()
        }

        fragment?.let { fragment ->
            holder.itemBinding.editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && holder.itemBinding.editText.text.isNotEmpty()) {
                    fragment.findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment(holder.itemBinding.editText.text.toString()))
                }
            }
        }
    }

    fun updateList(newList: List<Onboard>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setNextButtonClicked(clicked: Boolean) {
        isNextButtonClicked = clicked
        notifyDataSetChanged()
    }

    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }
}
