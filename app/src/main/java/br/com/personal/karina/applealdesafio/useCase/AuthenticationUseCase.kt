package br.com.personal.karina.applealdesafio.useCase

import android.content.Context
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.extensions.isEmailValid
import br.com.personal.karina.applealdesafio.repository.AuthenticationRepository
import java.lang.Exception

class AuthenticationUseCase(
    private val context: Context,
    private val repository: AuthenticationRepository
) {

    fun logInUser(
        email: String,
        password: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            error.invoke(Exception(context.getString(R.string.message_error_data_isempty)))
        } else if (email.isEmailValid().not()) {
            error.invoke((Exception(context.getString(R.string.message_error_email))))
        } else if (password.length < 6) {
            error.invoke(Exception(context.getString(R.string.message_password_lengt)))
        } else {
            repository.login(email, password, success, error)
        }
    }

    fun newRegister(
        email: String,
        password: String,
        confirmationPassword: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (email.isEmailValid().not()) {
            error.invoke(Exception(context.getString(R.string.message_error_email)))
        } else if (email.isEmpty() || password.isEmpty()) {
            error.invoke(Exception(context.getString(R.string.message_daata_register_empty)))
        } else if (password.length < 6) {
            error.invoke(Exception(context.getString(R.string.message_password_lengt)))

        } else if (password != confirmationPassword) {
            error.invoke(Exception(context.getString(R.string.message_password_not_equals)))
        } else {
            repository.newRegister(email, password, success, error)
        }
    }


}
