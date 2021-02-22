package br.com.personal.karina.applealdesafio.repository

import android.content.Context
import br.com.personal.karina.applealdesafio.data.Exercise
import br.com.personal.karina.applealdesafio.data.Training
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ExerciseRepository(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val referenceExercise: DatabaseReference,
) {

    private val databaseExercise by lazy {
        referenceExercise.child(EXERCISE)
    }

    fun findExerciseByTraining(
        idTraining: String,
        success: (List<Exercise>) -> Unit,
        error: (Exception) -> Unit
    ) {
        databaseExercise.child(firebaseAuth.uid.toString()).child(idTraining)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        it.getValue(Exercise::class.java)
                    }
                    success.invoke(list)
                }

                override fun onCancelled(errorFirebase: DatabaseError) {
                    error.invoke(errorFirebase.toException())
                }
            })
    }

    fun saveExercise(
        idTraining: String,
        exercise: Exercise,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        val nextId = databaseExercise.push().key
        exercise.id = nextId.toString()
        databaseExercise.child(firebaseAuth.uid.toString()).child(idTraining)
            .child(nextId.toString())
            .setValue(exercise)
            .addOnSuccessListener {
                success.invoke()
            }
            .addOnFailureListener {
                error.invoke(it)
            }
    }

    fun editExercise(
        idTraining: String,
        exercise: Exercise,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        exercise.id?.let { id ->
            databaseExercise.child(firebaseAuth.uid.toString())
                .child(idTraining)
                .child(id)
                .setValue(exercise)
                .addOnSuccessListener {
                    success.invoke()
                }
                .addOnFailureListener {
                    error.invoke(it)
                }
        }
    }


    fun deleteExercise(
        idTraining: String,
        idExercise: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        databaseExercise.child(firebaseAuth.uid.toString())
            .child(idTraining)
            .child(idExercise)
            .removeValue()
            .addOnSuccessListener {
                success.invoke()
            }
            .addOnFailureListener {
                error.invoke(it)
            }
    }

    fun deleteAllExerciseByTraining(
        idTraining: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        databaseExercise.child(firebaseAuth.uid.toString())
            .child(idTraining)
            .removeValue()
            .addOnSuccessListener {
                success.invoke()
            }.addOnFailureListener {
                error.invoke(it)
            }
    }


    companion object {
        const val EXERCISE = "EXERCISE"
    }
}