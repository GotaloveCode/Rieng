package com.miriga.rieng.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.miriga.rieng.R
import com.miriga.rieng.databinding.FragmentHomeBinding
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.Constants
import com.miriga.rieng.utils.PreferenceHelper.get

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val ageStr: String? = prefs[Constants.age]
        if(ageStr == null){
            findNavController().popBackStack(R.id.HomeFragment, true)
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setUpUI()

        return binding.root
    }

    private fun setUpUI() {
        binding.quiz.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_levelFragment)
        }
        binding.help.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_HelpCenterFragment)
        }
        binding.question.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_questionFragment)
        }
        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_AboutFragment)
        }
    }

}