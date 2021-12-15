package com.example.nurtdinov.ui.main.fragments.favorites

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nurtdinov.adapters.FavoriteRecipesAdapter
import com.example.nurtdinov.databinding.FragmentFavoriteRecipesBinding
import com.example.nurtdinov.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val binding by viewBinding<FragmentFavoriteRecipesBinding>(CreateMethod.INFLATE)
    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity()) }
    private val mainViewModel: MainViewModel by viewModels()
    private var mediatorLiveData = MediatorLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview(binding.favoriteRecipesRecyclerView)
        favoriteRecipeListener()

        mainViewModel.readFavoriteRecipe.observe(viewLifecycleOwner, { favoriteEntity ->
            mAdapter.setData(favoriteEntity)
        })

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

}