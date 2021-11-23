package com.example.nurtdinov.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nurtdinov.databinding.FragmentRecipesBinding
import com.todkars.shimmer.ShimmerRecyclerView


class RecipesFragment : Fragment() {
    private var _binding : FragmentRecipesBinding? = null
    private val mBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(layoutInflater,container,false)
        mBinding.recyclerview.showShimmer()
        return mBinding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}