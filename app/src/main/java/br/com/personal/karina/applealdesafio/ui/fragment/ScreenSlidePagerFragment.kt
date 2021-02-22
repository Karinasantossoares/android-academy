package br.com.personal.karina.applealdesafio.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.personal.karina.applealdesafio.data.Training
import br.com.personal.karina.applealdesafio.databinding.FragmentScreenSlidePagerBinding
import br.com.personal.karina.applealdesafio.extensions.toText
import br.com.personal.karina.applealdesafio.viewModel.TrainingViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*


class ScreenSlidePagerFragment(private val training: Training) : Fragment() {
    private lateinit var binding: FragmentScreenSlidePagerBinding
    private val viewModel: TrainingViewModel by sharedViewModel()
    private lateinit var onClickDelete: (Training) -> Unit
    private lateinit var onClickEdit: (Training) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenSlidePagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val training = arguments?.getParcelable<Training>(TRAINING)
        binding.titleTrainingViewPager.text = training?.name
        binding.descriptionTrainingViewPager.text = training?.description
        val date = Date().also { training?.date = it }
        binding.tvDateTraining.text = date.toText("dd/MM/yyyy")
        training?.let {

            binding.editTraining.setOnClickListener {
                onClickEdit.invoke(training)
            }
            binding.deleteTraining.setOnClickListener {
                onClickDelete.invoke(training)
            }
        }

    }

    fun setCallBacks(
        onClickDeleteCallBack: (Training) -> Unit,
        onClickEditCallBack: (Training) -> Unit
    ) {
        onClickDelete = onClickDeleteCallBack
        onClickEdit = onClickEditCallBack
    }


    companion object {
        const val TRAINING = "training"
        fun newInstance(training: Training): ScreenSlidePagerFragment {
            val args = Bundle()
            args.putParcelable(TRAINING, training)
            val fragment = ScreenSlidePagerFragment(training)
            fragment.arguments = args
            return fragment
        }
    }
}




