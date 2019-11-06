package com.miriga.rieng.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.miriga.rieng.R
import com.miriga.rieng.databinding.FragmentLoginBinding
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.Constants
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.PreferenceHelper.set
import com.miriga.rieng.utils.showToast


class LoginFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideLoginViewModelFactory(requireContext())
        loginViewModel = ViewModelProviders.of(this,factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.apply { 
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        loginViewModel.init()
//
//        val adapter = ArrayAdapter.createFromResource(
//            requireContext(), R.array.gender_array,
//            R.layout.spinner_item
//        )
//        adapter.setDropDownViewResource(R.layout.spinner_item)
//        binding.spinner.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel.getLoader().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })
        loginViewModel.getData().observe(this, Observer { data ->
            if(data!=null) {
                prefs[Constants.id] = data.data.id
                prefs[Constants.age] = data.data.age
                prefs[Constants.gender] = data.data.gender
                findNavController().navigate(R.id.action_loginFragment_to_HomeFragment)
            }
        })
        loginViewModel.getErrorUpdates().observe(this, Observer {
            activity?.showToast(it)
        })
    }


}
