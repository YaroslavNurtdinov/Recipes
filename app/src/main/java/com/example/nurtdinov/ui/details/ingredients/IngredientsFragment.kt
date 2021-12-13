package com.example.nurtdinov.ui.details.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nurtdinov.adapters.IngredientsAdapter
import com.example.nurtdinov.databinding.FragmentIngredientsBinding
import com.example.nurtdinov.models.Result
import com.example.nurtdinov.util.Constants.Companion.RECIPES_RESULT_KEY

class IngredientsFragment : Fragment() {

    private var _binding :FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter : IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myBundle: Result? = arguments?.getParcelable(RECIPES_RESULT_KEY)

        binding.ingredientsRecyclerview.adapter =  mAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}