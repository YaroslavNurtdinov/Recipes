package com.example.nurtdinov.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nurtdinov.R
import com.example.nurtdinov.databinding.IngredientsRowLayoutBinding
import com.example.nurtdinov.models.ExtendedIngredient
import com.example.nurtdinov.util.Constants.Companion.BASE_IMAGE_URL
import com.example.nurtdinov.util.RecipesDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<ExtendedIngredient>()

    fun setData(newIngredients:List<ExtendedIngredient>){
        val  ingredientsDiffUtil = RecipesDiffUtil(ingredientList,newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding =IngredientsRowLayoutBinding.bind(itemView)


        fun bind(ingredient:ExtendedIngredient) = with(binding){
            ingredientName.text = ingredient.name
            ingredientAmount.text = ingredient.amount.toString()
            ingredientUnit.text = ingredient.unit
            ingredientConsistency.text = ingredient.consistency
            ingredientOriginal.text = ingredient.original
            ingredientImageView.load(BASE_IMAGE_URL + ingredient.image){
                crossfade(600)
                error(R.drawable.ic_joke)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentIngredient = ingredientList[position]
        holder.bind(currentIngredient)
    }

    override fun getItemCount(): Int = ingredientList.size

}
