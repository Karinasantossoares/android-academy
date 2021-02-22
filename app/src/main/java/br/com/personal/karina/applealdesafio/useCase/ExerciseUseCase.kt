package br.com.personal.karina.applealdesafio.useCase

import android.content.Context
import android.net.Uri
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.data.Exercise
import br.com.personal.karina.applealdesafio.repository.ExercisePhotoRepository
import br.com.personal.karina.applealdesafio.repository.ExerciseRepository


class ExerciseUseCase(
    private val context: Context,
    private val exerciseRepository: ExerciseRepository,
    private val exercisePhotoRepository: ExercisePhotoRepository
) {

    fun findExerciseByTraining(
        idTraining: String,
        success: (List<Exercise>) -> Unit,
        error: (Exception) -> Unit
    ) {
        exerciseRepository.findExerciseByTraining(idTraining, success, error)
    }


    fun saveExercise(
        idTraining: String,
        exercise: Exercise,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (exercise.name.isEmpty() || exercise.observation.isEmpty()) {
            error.invoke(Exception(context.getString(R.string.message_data_invalid)))
        } else {
            exerciseRepository.saveExercise(idTraining, exercise, success, error)
        }

    }

    fun editExercise(
        trainingId: String,
        exercise: Exercise,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (exercise.name.isEmpty() || exercise.observation.isEmpty()) {
            error.invoke(Exception(context.getString(R.string.message_data_invalid)))
        } else {
            exerciseRepository.editExercise(trainingId, exercise, success, error)
        }
    }


    fun deleteExercise(
        idTraining: String,
        idExercise: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        exerciseRepository.deleteExercise(idTraining, idExercise, success, error)
    }

    fun uploudPhoto(
        filePath: Uri,
        idTraining: String,
        idExercise: String,
        success: (String) -> Unit,
        error: (Exception) -> Unit
    ) {
        exercisePhotoRepository.uploudPhoto(filePath, idTraining, idExercise, success, error)
    }


    fun deletePhoto(
        url: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        exercisePhotoRepository.deletePhoto(url, success, error)
    }

}
