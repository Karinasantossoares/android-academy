package br.com.personal.karina.applealdesafio.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class AuthenticationRepository(
    private val firebaseAuth: FirebaseAuth
) {

    fun login(
        email: String,
        password: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {

        firebaseAuth.signOut()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            success.invoke()
        }.addOnFailureListener {
            error.invoke(it)
        }
    }


    fun newRegister(
        email: String,
        password: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            success.invoke()
        }.addOnFailureListener {
            error.invoke(it)
        }
    }
}