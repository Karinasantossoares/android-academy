package br.com.personal.karina.applealdesafio.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.data.Exercise
import br.com.personal.karina.applealdesafio.databinding.FragmentNewExerciseBinding
import br.com.personal.karina.applealdesafio.viewModel.TrainingViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.sharedViewModel


class NewExerciseFragment : Fragment() {
    private lateinit var binding: FragmentNewExerciseBinding
    private val viewModel: TrainingViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.clearLiveDatas()

        binding.btnSaveNewExercise.setOnClickListener {
            val nameExercise = binding.etNameExercise.text.toString()
            val descriprition = binding.etDescriptionExercise.text.toString()
            viewModel.saveExerciseWithImage(nameExercise,descriprition)
        }

        binding.changedImage.setOnClickListener {
            selectedPhoto()
        }


        viewModel.selectedExerciseLiveData.observe(viewLifecycleOwner, Observer {
            binding.titleNewExercise.text = getString(R.string.edit_exercise)
            binding.etNameExercise.setText(it.name)
            binding.etDescriptionExercise.setText(it.observation)
            it.image?.let {image->
                Picasso.get()
                    .load(image)
                    .fit()
                    .centerCrop()
                    .into(binding.imageNewExercise)
            }


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

        viewModel.loadLiveData.observe(viewLifecycleOwner,Observer {
            binding.pbLoadExercise.isVisible = it
        })



    }

    fun selectedPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val myUri = data?.data
        if (requestCode == REQUEST_CODE_GALERY && resultCode == Activity.RESULT_OK) { // Ação do usuário de escolher foto / Ac...Ação cancelar ou OK)
            val bitmap: Bitmap
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, myUri)
                binding.imageNewExercise.setImageBitmap(bitmap)
                myUri?.let {
                    viewModel.setUri(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyExercise()
    }

    companion object {
        private const val REQUEST_CODE_GALERY = 1518
    }

}
