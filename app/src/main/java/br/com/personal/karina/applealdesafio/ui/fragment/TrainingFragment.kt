package br.com.personal.karina.applealdesafio.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.ui.adapter.ExerciseAdapter
import br.com.personal.karina.applealdesafio.ui.adapter.ScreenPagerTrainingAdapter
import br.com.personal.karina.applealdesafio.data.Training
import br.com.personal.karina.applealdesafio.databinding.FragmentTrainingBinding
import br.com.personal.karina.applealdesafio.viewModel.TrainingViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class TrainingFragment() : Fragment() {
    private lateinit var binding: FragmentTrainingBinding
    private val viewModel: TrainingViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clearLiveDatas()

        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.action_trainingFragment_to_newExerciseFragment)
        }

        binding.tvAddTraining.setOnClickListener {
            findNavController().navigate(R.id.action_trainingFragment_to_newTraining2)
        }

        binding.srTraining.setOnRefreshListener {
            viewModel.findAllTraining()
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.findExerciseByTraining(position)

            }
        })

        binding.viewPager.currentItem = binding.viewPager.currentItem

        viewModel.visibilityItemViewLiveData.observe(viewLifecycleOwner, Observer {
            binding.fbAdd.isVisible = it
            binding.tvTrainingCreate.isVisible = it
        })
        viewModel.showMessageListExerciseEmpty.observe(viewLifecycleOwner, Observer {
            binding.tvExerciseEmpty.isVisible = it
        })

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoad.isVisible = it
            binding.srTraining.isRefreshing = it
        })

        viewModel.showMessageListEmpityLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvTrainingCreate.isVisible = it
        })

        viewModel.successeditLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.errorEditLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.deleteSuccessLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.deleteErrorLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.errorUploudPhotoLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.errorListTrainingLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.successListTrainingLiveData.observe(viewLifecycleOwner, Observer {
            val listFragments = it.map { training ->
                return@map createFragmentTraining(training)
            }

            val adapterViewPager = ScreenPagerTrainingAdapter(this, listFragments)
            binding.viewPager.adapter = adapterViewPager
        })

        viewModel.errorListTrainingLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.successListExerciseLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = ExerciseAdapter(it, onClickDelete = { exercise ->
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.message_delete)
                    .setPositiveButton(R.string.messag_ok) { _, _ ->
                        viewModel.deleteExercise(exercise)
                    }.create().show()
            },
                onClickEdit = { editExercise ->
                    viewModel.selectedExercise(editExercise)
                    findNavController().navigate(R.id.action_trainingFragment_to_newExerciseFragment)
                })
            binding.rvMyRecycler.adapter = adapter
        })

        binding.viewPager.offscreenPageLimit = 3
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
        binding.viewPager.setPageTransformer { page: View, position: Float ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            when {
                position < -1 -> {
                    page.translationX = -myOffset
                }
                position <= 1 -> {
                    val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                }
                else -> {
                    page.translationX = myOffset
                }
            }

        }

    }

    private fun createFragmentTraining(training: Training): ScreenSlidePagerFragment {
        val fragment = ScreenSlidePagerFragment.newInstance(training)
        fragment.setCallBacks(
            onClickDeleteCallBack = { idTrainingDelete ->
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.message_delete)
                    .setPositiveButton(R.string.messag_ok) { _, _ ->
                        viewModel.deleteTraining(idTrainingDelete)

                    }.create().show()
            },
            onClickEditCallBack = { training ->
                viewModel.selectedEditTraining(training)
                findNavController().navigate(R.id.action_trainingFragment_to_newTraining2)

            })
        return fragment
    }


}



