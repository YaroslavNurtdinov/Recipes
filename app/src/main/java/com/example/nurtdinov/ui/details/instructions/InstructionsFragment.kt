package com.example.nurtdinov.ui.details.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nurtdinov.databinding.FragmentInstructionsBinding
import com.example.nurtdinov.models.Result
import com.example.nurtdinov.util.Constants.Companion.RECIPES_RESULT_KEY

class InstructionsFragment : Fragment() {

    private val binding by viewBinding<FragmentInstructionsBinding>(CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myBundle: Result? = arguments?.getParcelable(RECIPES_RESULT_KEY)

        binding.apply {
            instructionsWebView.webViewClient = object : WebViewClient() {}
            instructionsWebView.loadUrl(myBundle!!.sourceUrl)
        }

    }
}