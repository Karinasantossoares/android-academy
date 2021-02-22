package br.com.personal.karina.applealdesafio.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.databinding.FragmentLoginBinding
import br.com.personal.karina.applealdesafio.ui.component.BiometricView
import br.com.personal.karina.applealdesafio.viewModel.AuthenticationViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthenticationViewModel by sharedViewModel()
    private val biometricManager: BiometricManager by lazy { BiometricManager.from(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val biometricView = BiometricView(this)
        viewModel.checkBiometric(biometricManager)

        binding.btnEnter.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.tvNewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_RegisterUserFragment)
        }

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoad.isVisible = it
            binding.btnEnter.isVisible = !it
        })

        viewModel.errorLoginLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvMessageError.isVisible = true
            binding.tvMessageError.text = it
        })

        viewModel.successLoginLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            requireActivity().finish()
        })

        viewModel.checkDeviceLiveData.observe(viewLifecycleOwner, Observer {
            binding.ivBiometric.isVisible = it
            binding.ivBiometric.setOnClickListener {
                biometricView.showDialogBiometric(successCallBack = {viewModel.loginWithViewModel()},{})
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onClearLiveDatas()
    }
}

