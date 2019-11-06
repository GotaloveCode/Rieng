package com.miriga.rieng.ui.ask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miriga.rieng.databinding.FragmentQuestionBinding
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast

class QuestionFragment : BaseFragment() {

    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideQuestionViewModelFactory(requireContext())
        questionViewModel = ViewModelProviders.of(this,factory).get(QuestionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = questionViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        questionViewModel.init()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        questionViewModel.getLoader().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })
        questionViewModel.getErrorUpdates().observe(this, Observer { data ->
            if(data!=null) {
               activity?.showToast(data)
            }
        })
        questionViewModel.getData().observe(viewLifecycleOwner, Observer {
            activity?.showToast("Question posted successfully!We will get back to you on the contact provided")
        })
    }
}