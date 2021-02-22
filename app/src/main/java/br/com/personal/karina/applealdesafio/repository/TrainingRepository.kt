package br.com.personal.karina.applealdesafio.repository

import android.content.Context
import br.com.personal.karina.applealdesafio.data.Exercise
import br.com.personal.karina.applealdesafio.data.Training
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.Exception

class TrainingRepository(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val referenceTraining: DatabaseReference,
    private val exerciseRepository: ExerciseRepository

) {
    private val databaseTraining by lazy {
        referenceTraining.child(TRAINING)
    }

    fun findAllTraining(
        success: (List<Training>) -> Unit,
        error: (DatabaseException) -> Unit
    ) {
        databaseTraining.child(firebaseAuth.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        it.getValue(Training::class.java)
                    }
                    success.invoke(list)
                }

                override fun onCancelled(errorFirebase: DatabaseError) {
                    error.invoke(errorFirebase.toException())
                }
            })
    }

    fun saveTraining(
        training: Training,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        val nextId = databaseTraining.push().key
        training.id = nextId
        databaseTraining.child(firebaseAuth.uid.toString()).child(nextId.toString())
            .setValue(training)
            .addOnSuccessListener {
                success.invoke()
            }
            .addOnFailureListener {
                error.invoke(it)
            }
    }

    fun editTraining(
        training: Training,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        training.id?.let { id ->
            databaseTraining.child(firebaseAuth.uid.toString())
                .child(id)
                .setValue(training)
                .addOnSuccessListener {
                    success.invoke()
                }
                .addOnFailureListener {
                    error.invoke(it)
                }
        }
    }


    fun deleteTraining(
        idTraining: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        databaseTraining.child(firebaseAuth.uid.toString())
            .child(idTraining)
            .removeValue()
            .addOnSuccessListener {
                exerciseRepository.deleteAllExerciseByTraining(idTraining, success, error)
            }
            .addOnFailureListener {
                error.invoke(it)
            }
    }


    companion object {
        const val TRAINING = "TRAINING"
    }
}
