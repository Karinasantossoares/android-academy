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
import br.com.personal.karina.applealdesafio.data.Training
import br.com.personal.karina.applealdesafio.databinding.FragmentNewTrainingBinding
import br.com.personal.karina.applealdesafio.viewModel.TrainingViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class NewTraining : Fragment() {
    private lateinit var binding: FragmentNewTrainingBinding
    private val viewModel: TrainingViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clearLiveDatas()

        binding.btnSaveNewTraining.setOnClickListener {
            val nameTraining = binding.etNameTraining.text.toString()
            val descriptionTraining = binding.etDescriptionTraining.text.toString()
            viewModel.saveOrUpdateTraining(nameTraining,descriptionTraining)
        }

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoadTraining.isVisible = it
        })

        viewModel.successSaveLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })

        viewModel.errorSaveLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.successeditLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })

        viewModel.errorEditLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.editTrainingLiveData.observe(viewLifecycleOwner, Observer {
            binding.etNameTraining.setText(it.name)
            binding.etDescriptionTraining.setText(it.description)
            binding.tvTitleNewTraining.text = getString(R.string.message_edit_training)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyTraining()
    }
}