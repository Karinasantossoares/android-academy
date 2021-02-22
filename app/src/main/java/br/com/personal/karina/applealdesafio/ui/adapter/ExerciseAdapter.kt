package br.com.personal.karina.applealdesafio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.data.Exercise
import br.com.personal.karina.applealdesafio.databinding.ItemListExerciseBinding
import com.squareup.picasso.Picasso

class ExerciseAdapter(
    private val exercise: List<Exercise>,
    private val onClickDelete: (Exercise) -> Unit,
    private val onClickEdit: (Exercise) -> Unit

    ) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun getItemCount() = exercise.count()

    override fun onBindViewHolder(holder: ExerciseAdapter.ExerciseViewHolder, position: Int) {
        holder.bindView(exercise[position])
    }


    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListExerciseBinding.bind(itemView)

        fun bindView(exercise: Exercise) {
            binding.nameExercise.text = exercise.name
            binding.tvDescriptionExerciseList.text = exercise.observation

            exercise.image?.let {
                Picasso.get()
                    .load(exercise.image)
                    .fit()
                    .centerCrop()
                    .into(binding.imageNewExercise)
            }
            binding.deleteExercise.setOnClickListener {
                onClickDelete.invoke(exercise)
            }
            binding.editExercise.setOnClickListener {
                onClickEdit.invoke(exercise)
            }
        }

    }

}