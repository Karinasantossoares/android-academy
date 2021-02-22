package br.com.personal.karina.applealdesafio.repository

import android.net.Uri
import br.com.personal.karina.applealdesafio.data.Training
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class ExercisePhotoRepository(
    private val storageReference: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth
) {
    private val firebaseStorage by lazy {
        storageReference.reference.child(EXERCISE)
    }

    fun uploudPhoto(
        filePath: Uri,
        idTraining: String,
        idExercise: String,
        success: (String) -> Unit,
        error: (Exception) -> Unit
    ) {
        val firebaseStorageFile = firebaseStorage
            .child(firebaseAuth.uid.toString())
            .child(idTraining)
            .child(idExercise)

        firebaseStorageFile.putFile(filePath).addOnSuccessListener {
            firebaseStorageFile.downloadUrl.addOnSuccessListener { uri ->
                success.invoke(uri.toString())
            }.addOnFailureListener {
                it.printStackTrace()
            }
        }.addOnFailureListener {
            error.invoke(it)
        }
    }

    fun deletePhoto(
        url: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        storageReference.getReferenceFromUrl(url)
            .delete()
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
