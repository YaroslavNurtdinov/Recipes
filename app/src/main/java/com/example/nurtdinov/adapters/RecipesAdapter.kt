package com.example.nurtdinov.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nurtdinov.R
import com.example.nurtdinov.databinding.RecipesRowLayoutBinding
import com.example.nurtdinov.models.FoodRecipe
import com.example.nurtdinov.models.Result
import com.example.nurtdinov.ui.fragments.recipes.RecipesFragmentDirections
import com.example.nurtdinov.util.RecipesDiffUtil
import org.jsoup.Jsoup

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {
    private var recipes = emptyList<Result>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RecipesRowLayoutBinding.bind(itemView)

        fun parseHtml(textView: TextView, description:String?){
            if(description != null){
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }

        }

        fun bind(result: Result) = with(binding) {
            recipeImageView.load(result.image) {
                crossfade(300)
            }
            titleTextView.text = result.title
           // descriptionTextView.text = result.summary
            parseHtml(descriptionTextView,result.summary)
            heartTextView.text = result.aggregateLikes.toString()
            clockTextView.text = result.readyInMinutes.toString()

            binding.root.setOnClickListener {
                Log.d("Called","called")
                try {
                    val action =
                        RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    binding.root.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("RowClick", e.toString())
                }

            }
        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipes_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)

        if (currentRecipe.vegan) {
            holder.itemView.findViewById<TextView>(R.id.leaf_textView)
                .setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            holder.itemView.findViewById<ImageView>(R.id.leaf_imageView)
                .setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
    }

    override fun getItemCount(): Int = recipes.size

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }


}