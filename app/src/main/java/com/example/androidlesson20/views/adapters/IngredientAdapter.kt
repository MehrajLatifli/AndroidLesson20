package com.example.androidlesson20.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson20.databinding.ItemIngredientBinding

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private val ingredientsList = mutableListOf<String>()

    inner class IngredientViewHolder(val itemIngredientBinding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(itemIngredientBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.itemIngredientBinding.textView.text = ingredient
    }

    fun updateList(newList: List<String>) {
        ingredientsList.clear()
        ingredientsList.addAll(newList)
        notifyDataSetChanged()
    }
}