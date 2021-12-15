package com.example.nurtdinov.adapters

import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nurtdinov.R
import com.example.nurtdinov.data.database.entities.FavoritesEntity
import com.example.nurtdinov.databinding.FavoriteRecipesRowLayoutBinding
import com.example.nurtdinov.ui.main.fragments.favorites.FavoriteRecipesFragmentDirections
import com.example.nurtdinov.util.RecipesDiffUtil
import org.jsoup.Jsoup

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(),
    ActionMode.Callback {

    private var favoriteRecipe = emptyList<FavoritesEntity>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FavoriteRecipesRowLayoutBinding.bind(itemView)
        val favoriteRecipeRowLayout: ConstraintLayout =
            itemView.findViewById(R.id.favorite_recipesRowLayout)

        fun bind(favoriteEntity: FavoritesEntity) = with(binding) {
            favoriteRecipeImageView.load(favoriteEntity.result.image) {
                crossfade(300)
            }
            favoriteTitleTextView.text = favoriteEntity.result.title
            parseHtml(favoriteDescriptionTextView, favoriteEntity.result.summary)
            favoriteHeartTextView.text = favoriteEntity.result.aggregateLikes.toString()
            favoriteClockTextView.text = favoriteEntity.result.readyInMinutes.toString()

        }

        private fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_recipes_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipe[position]
        holder.bind(selectedRecipe)

        /**
         * Single Click Listener
         * */
        holder.favoriteRecipeRowLayout.setOnClickListener {
            val action =
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                    selectedRecipe.result
                )
            holder.itemView.findNavController().navigate(action)
        }

        /**
         * Long Click Listener
         * */
        holder.favoriteRecipeRowLayout.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int = favoriteRecipe.size

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color:Int){
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity,color)
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipe, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipe = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}


