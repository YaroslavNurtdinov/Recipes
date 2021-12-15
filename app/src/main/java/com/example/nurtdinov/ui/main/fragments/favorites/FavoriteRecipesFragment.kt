package com.example.nurtdinov.ui.main.fragments.favorites

import android.os.Bundle
import android.view.*

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nurtdinov.R
import com.example.nurtdinov.adapters.FavoriteRecipesAdapter
import com.example.nurtdinov.databinding.FragmentFavoriteRecipesBinding
import com.example.nurtdinov.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private val binding by viewBinding<FragmentFavoriteRecipesBinding>(CreateMethod.INFLATE)

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(
            requireActivity(),
            mainViewModel
        )
    }
    private var mediatorLiveData = MediatorLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        setupRecyclerview(binding.favoriteRecipesRecyclerView)
        favoriteRecipeListener()

        mainViewModel.readFavoriteRecipe.observe(viewLifecycleOwner, { favoriteEntity ->
            mAdapter.setData(favoriteEntity)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_favorite_recipes) {
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerview(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun favoriteRecipeListener() {
        mediatorLiveData.addSource(mainViewModel.readFavoriteRecipe) {
            mediatorLiveData.value = it.isNullOrEmpty()
        }

        mediatorLiveData.observe(viewLifecycleOwner, {
            if (it) visibleNotice()
            else invisibleNotice()
        })
    }

    private fun visibleNotice() {
        binding.noDataImageView.visibility = View.VISIBLE
        binding.noDataTextView.visibility = View.VISIBLE
    }

    private fun invisibleNotice() {
        binding.noDataImageView.visibility = View.INVISIBLE
        binding.noDataTextView.visibility = View.INVISIBLE
    }

    private fun showSnackBar(){
        Snackbar.make(
            binding.root,
            "All recipes removed.",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.clearContextualActionMode()
    }
}