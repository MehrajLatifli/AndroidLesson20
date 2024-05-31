package com.example.androidlesson20.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson20.databinding.ItemRecipeBinding
import com.example.androidlesson20.models.get.Recipe

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.UserViewHolder>() {

    private val List = arrayListOf<Recipe>()

    lateinit var onClickItem: (String) -> Unit

    inner class UserViewHolder(val itemRecipeBinding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(itemRecipeBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val item = List[position]

        holder.itemRecipeBinding.recipe =item



            holder.itemRecipeBinding.recipe = item

        holder.itemRecipeBinding.Detailcardview.setOnClickListener {
            onClickItem.invoke(item.id.toString())
        }



    }

    fun updateList(newList: List<Recipe>) {
        List.clear()
        List.addAll(newList)
        notifyDataSetChanged()
    }
}