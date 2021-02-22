package br.com.personal.karina.applealdesafio.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.databinding.FragmentRegisterUserBinding
import br.com.personal.karina.applealdesafio.viewModel.AuthenticationViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class RegisterUserFragment : Fragment() {
    private lateinit var binding: FragmentRegisterUserBinding
    private val viewModel: AuthenticationViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmationPassword = binding.etConfirmationPassword.text.toString()
            viewModel.newRegisterUser(email, password, confirmationPassword)
        }

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoadRegister.isVisible = it
            binding.btnRegister.isVisible = !it
        })

        viewModel.successLoginLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_registerUserFragment_to_mainActivity)
            requireActivity().finish()
        })

        viewModel.errorRegisterLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

    }





}