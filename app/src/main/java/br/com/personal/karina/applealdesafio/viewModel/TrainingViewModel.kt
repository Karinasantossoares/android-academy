package br.com.personal.karina.applealdesafio.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.data.Exercise
import br.com.personal.karina.applealdesafio.data.Training
import br.com.personal.karina.applealdesafio.useCase.ExerciseUseCase
import br.com.personal.karina.applealdesafio.useCase.TrainingUseCase

class TrainingViewModel(
    private val context: Context,
    private val useCaseTraining: TrainingUseCase,
    private val useCaseExercise: ExerciseUseCase
) : ViewModel() {

    var loadLiveData = MutableLiveData<Boolean>()
    var successSaveLiveData = MutableLiveData<String>()
    var errorSaveLiveData = MutableLiveData<String>()
    var successListTrainingLiveData = MutableLiveData<List<Training>>()
    var successListExerciseLiveData = MutableLiveData<List<Exercise>>()
    var selectedTrainingLiveData = MutableLiveData<Training>()
    var selectedExerciseLiveData = MutableLiveData<Exercise>()
    var deleteSuccessLiveData = MutableLiveData<String>()
    var deleteErrorLiveData = MutableLiveData<String>()
    var successeditLiveData = MutableLiveData<String>()
    var errorEditLiveData = MutableLiveData<String>()
    var errorListTrainingLiveData = MutableLiveData<String>()
    var visibilityItemViewLiveData = MutableLiveData<Boolean>()
    val errorlistExerciseLiveData = MutableLiveData<String>()
    val showMessageListEmpityLiveData = MutableLiveData<Boolean>()
    var showMessageListExerciseEmpty = MutableLiveData<Boolean>()
    var uriPhotoExerciseLiveData = MutableLiveData<Uri>()
    var errorUploudPhotoLiveData = MutableLiveData<String>()
    var editTrainingLiveData = MutableLiveData<Training>()

    init {
        findAllTraining()
    }

    fun saveOrUpdateTraining(nameTraining: String, description: String) {
        if (editTrainingLiveData.value == null) {
            saveTraining(Training(name = nameTraining, description = description))
        } else {
            editTrainingLiveData.value?.name = nameTraining
            editTrainingLiveData.value?.description = description
            editTraining()
        }
    }

    fun saveTraining(training: Training) {
        loadLiveData.value = true
        useCaseTraining.saveTraining(training,
            success = {
                loadLiveData.value = false
                successSaveLiveData.value = context.getString(R.string.message_training_save)
            },
            error = {
                loadLiveData.value = false
                errorSaveLiveData.value = it.localizedMessage
            })
    }

    fun deleteTraining(training: Training?) {
        loadLiveData.value = true
        training?.id?.let { id ->
            useCaseTraining.deleteTraining(id,
                success = {
                    loadLiveData.value = false
                    deleteSuccessLiveData.value =
                        context.getString(R.string.message_delete_success)
                },
                error = {
                    loadLiveData.value = false
                    deleteErrorLiveData.value = it.localizedMessage
                })
        }

    }

    fun editTraining() {
        loadLiveData.value = true
        editTrainingLiveData.value?.let {
            useCaseTraining.editTraining(it,
                success = {
                    loadLiveData.value = false
                    successeditLiveData.value = context.getString(R.string.message_succes_edit)
                },
                error = {
                    loadLiveData.value = false
                    errorEditLiveData.value = it.localizedMessage
                })
        }
    }

    fun findAllTraining() {
        loadLiveData.value = true
        useCaseTraining.findAllTraining(
            success = {
                loadLiveData.value = false
                if (it.isNullOrEmpty()) {
                    visibilityItemViewLiveData.value = false
                    showMessageListEmpityLiveData.value = true
                } else {
                    visibilityItemViewLiveData.value = true
                    showMessageListEmpityLiveData.value = false
                }
                successListTrainingLiveData.value = it

            },
            errorDatabase = {
                loadLiveData.value = false
                showMessageListEmpityLiveData.value = true
                errorListTrainingLiveData.value =
                    context.getString(R.string.message_error_list_training)
            })
    }

    fun selectedEditTraining(training: Training) {
        editTrainingLiveData.value = training
    }

    fun onDestroyTraining() {
        editTrainingLiveData = MutableLiveData()
    }

    fun selectedExercise(exercise: Exercise) {
        selectedExerciseLiveData.value = exercise
    }

    fun saveExerciseWithImage(nameExercise: String, descriptionExercise: String) {
        val uri = uriPhotoExerciseLiveData.value
        selectedExerciseLiveData.value?.name = nameExercise
        selectedExerciseLiveData.value?.observation = descriptionExercise

        val exercise = selectedExerciseLiveData.value ?: Exercise(name = nameExercise, observation = descriptionExercise)
        if (uri != null) {
            uploudPhotoExercise(exercise.date.time.toString(), uri) {
                exercise.image = it
                saveOrUpdateExercise(exercise)
            }
        } else {
            saveOrUpdateExercise(exercise)
        }
    }


    private fun saveOrUpdateExercise(exercise: Exercise) {
        if (selectedExerciseLiveData.value == null) {
            saveExercise(exercise)
        } else {
            editExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        loadLiveData.value = true
        val idTraining = selectedTrainingLiveData.value?.id
        idTraining?.let { trainingId ->
            exercise.id?.let { idExercise ->
                useCaseExercise.deleteExercise(trainingId, idExercise,
                    success = {
                        loadLiveData.value = false
                        exercise.image?.let {
                            deletePhoto(it,
                                success = {
                                    loadLiveData.value = false
                                    deleteSuccessLiveData.value =
                                        context.getString(R.string.message_delete_success)
                                }, error = {
                                    loadLiveData.value = false
                                    deleteErrorLiveData.value = it.localizedMessage
                                })
                        }

                    }, error = {
                        loadLiveData.value = false
                        deleteErrorLiveData.value = it.localizedMessage
                    })
            }
        }
    }

    private fun editExercise(exercise: Exercise) {
        loadLiveData.value = true
        val idTraining = selectedTrainingLiveData.value?.id
        idTraining?.let { trainingId ->
            useCaseExercise.editExercise(trainingId, exercise,
                success = {
                    loadLiveData.value = false
                    successeditLiveData.value =
                        context.getString(R.string.message_sucess_edit_exercise)
                }, error = {
                    loadLiveData.value = false
                    errorEditLiveData.value = it.localizedMessage
                })
        }


    }

    private fun saveExercise(
        exercise: Exercise,
    ) {
        loadLiveData.value = true
        val id = selectedTrainingLiveData.value?.id
        id?.let {
            useCaseExercise.saveExercise(id, exercise,
                success = {
                    loadLiveData.value = false
                    successSaveLiveData.value =
                        context.getString(R.string.message_success_save_exercise)
                }, error = {
                    loadLiveData.value = false
                    errorSaveLiveData.value = it.localizedMessage

                })
        }

    }


    fun findExerciseByTraining(position: Int) {
        loadLiveData.value = true
        selectedTrainingLiveData.value = successListTrainingLiveData.value?.get(position)
        val id = selectedTrainingLiveData.value?.id
        id?.let { idTraining ->
            useCaseExercise.findExerciseByTraining(idTraining, success = {
                loadLiveData.value = false
                showMessageListExerciseEmpty.value = it.isNullOrEmpty()
                successListExerciseLiveData.value = it
            }, error = {
                showMessageListExerciseEmpty.value = true
                loadLiveData.value = false
                errorlistExerciseLiveData.value =
                    context.getString(R.string.message_error_list_exercise)
            })
        }
    }

    fun uploudPhotoExercise(timestemp: String, uri: Uri, success: (String) -> Unit) {
        loadLiveData.value = true
        val idTraining = selectedTrainingLiveData.value?.id
        idTraining?.let {
            useCaseExercise.uploudPhoto(uri, idTraining, timestemp, success,
                error = { e ->
                    loadLiveData.value = false
                    errorUploudPhotoLiveData.value = e.localizedMessage
                })
        }
    }

    private fun deletePhoto(
        urlImage: String,
        success: () -> Unit,
        error: (Exception) -> Unit,
    ) {
        loadLiveData.value = true
        useCaseExercise.deletePhoto(urlImage, success, error)


    }


    fun clearLiveDatas() {
        loadLiveData = MutableLiveData()
        errorSaveLiveData = MutableLiveData()
        successSaveLiveData = MutableLiveData()
        deleteErrorLiveData = MutableLiveData()
        deleteSuccessLiveData = MutableLiveData()
        errorEditLiveData = MutableLiveData()
        successeditLiveData = MutableLiveData()

    }


    fun onDestroyExercise() {
        selectedExerciseLiveData = MutableLiveData()
        uriPhotoExerciseLiveData = MutableLiveData()
    }

    fun setUri(uri: Uri) {
        uriPhotoExerciseLiveData.value = uri
    }

}