package com.example.nurtdinov.ui.details.overview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.nurtdinov.R
import com.example.nurtdinov.databinding.FragmentOverviewBinding
import com.example.nurtdinov.models.Result
import com.example.nurtdinov.util.Constants.Companion.RECIPES_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPES_RESULT_KEY)

        binding.apply {
            mainImageView.load(myBundle?.image)
            titleTextView.text = myBundle?.title
            likesTextView.text = myBundle?.aggregateLikes.toString()
            timeTextView.text = myBundle?.readyInMinutes.toString()
            myBundle?.summary.let {
                val summary = Jsoup.parse(it).text()
                summaryTextView.text = summary
            }
        }

        if (myBundle?.vegetarian == true) {
            binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        if (myBundle?.vegan == true) {
            binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        if (myBundle?.glutenFree == true) {
            binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        if (myBundle?.cheap == true) {
            binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }


    }
}