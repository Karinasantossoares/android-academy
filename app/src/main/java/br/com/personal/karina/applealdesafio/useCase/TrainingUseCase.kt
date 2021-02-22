package br.com.personal.karina.applealdesafio.useCase

import android.content.Context
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.data.Training
import br.com.personal.karina.applealdesafio.repository.TrainingRepository
import com.google.firebase.database.DatabaseException

class TrainingUseCase(
    private val context: Context,
    private val trainingRepository: TrainingRepository
) {

    fun saveTraining(
        training: Training,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (training.name.isEmpty()  || training.description.isEmpty()) {
            error.invoke(Exception(context.getString(R.string.message_error_isempty)))
        } else {
            trainingRepository.saveTraining(training, success, error)
        }
    }

    fun deleteTraining(
        idTraining: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        trainingRepository.deleteTraining(idTraining, success, error)
    }

    fun editTraining(
        training: Training,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (training.name.isEmpty() ||  training.description.isEmpty()) {
            error.invoke(Exception(context.getString(R.string.message_error_isempty)))
        } else {
            trainingRepository.editTraining(training, success, error)
        }

    }

    fun findAllTraining(
        success: (List<Training>) -> Unit,
        errorDatabase: (DatabaseException) -> Unit
    ) {
        trainingRepository.findAllTraining(success, errorDatabase)
    }
}