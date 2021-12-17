package com.example.nurtdinov.ui.main.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nurtdinov.R
import com.example.nurtdinov.databinding.FragmentFoodJokeBinding
import com.example.nurtdinov.util.Constants.Companion.API_KEY
import com.example.nurtdinov.util.NetworkResult
import com.example.nurtdinov.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private val binding by viewBinding<FragmentFoodJokeBinding>(CreateMethod.INFLATE)
    private val mainViewModel by viewModels<MainViewModel>()
    private var foodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        requestApiData()
    }

    private fun requestApiData() {
        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.apply {
                        foodJokeTextView.text = response.data?.text
                        if(response.data !=null){
                            foodJoke = response.data.text
                        }
                        progressBar.visibility = View.INVISIBLE
                        foodJokeCardView.visibility = View.VISIBLE
                        foodJokeErrorTextView.visibility = View.INVISIBLE
                        foodJokeErrorImageView.visibility = View.INVISIBLE
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCash()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading...")
                    binding.apply {
                        foodJokeCardView.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun loadDataFromCash() {
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner, { database ->
                if (!database.isNullOrEmpty()) {
                    binding.apply {
                        foodJokeTextView.text = database[0].foodJoke.text
                        foodJoke = database[0].foodJoke.text
                        progressBar.visibility = View.INVISIBLE
                        foodJokeCardView.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        foodJokeErrorTextView.visibility = View.VISIBLE
                        foodJokeErrorImageView.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.share_food_joke_menu){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}