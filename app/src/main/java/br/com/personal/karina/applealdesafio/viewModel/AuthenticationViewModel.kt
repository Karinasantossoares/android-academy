package br.com.personal.karina.applealdesafio.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.biometrics.BiometricManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.personal.karina.applealdesafio.persistence.AppPreferences
import br.com.personal.karina.applealdesafio.useCase.AuthenticationUseCase

class AuthenticationViewModel(
    private val context: Context,
    private val authenticationUseCase: AuthenticationUseCase,
    private val preferences: AppPreferences
) : ViewModel() {
    var loadLiveData = MutableLiveData<Boolean>()
    var successLoginLiveData = MutableLiveData<Any>()
    var errorLoginLiveData = MutableLiveData<String>()
    var errorRegisterLiveData = MutableLiveData<String>()
    val checkDeviceLiveData = MutableLiveData<Boolean>()


    fun loginWithViewModel() {
        val email = preferences.getStringByKey(AppPreferences.EMAIL) ?: ""
        val password = preferences.getStringByKey(AppPreferences.PASSWORD) ?: ""
        login(email, password)
    }


    fun login(
        email: String,
        password: String
    ) {
        loadLiveData.value = true
        authenticationUseCase.logInUser(
            email, password,
            success = {
                loadLiveData.value = false
                successLoginLiveData.value = Any()
                preferences.saveStringKey(AppPreferences.EMAIL, email)
                preferences.saveStringKey(AppPreferences.PASSWORD, password)
            },
            error = {
                loadLiveData.value = false
                errorLoginLiveData.value = it.localizedMessage

            })
    }

    fun newRegisterUser(
        email: String,
        password: String,
        confirmationPassword: String
    ) {
        loadLiveData.value = true
        authenticationUseCase.newRegister(
            email,
            password,
            confirmationPassword,
            success = {
                loadLiveData.value = false
                login(email, password)
            },
            error = {
                loadLiveData.value = false
                errorRegisterLiveData.value = it.localizedMessage

            }
        )

    }

    fun onClearLiveDatas() {
        loadLiveData = MutableLiveData()
        errorRegisterLiveData = MutableLiveData()
    }

    @SuppressLint("WrongConstant")
    fun checkBiometric(biometricManager: androidx.biometric.BiometricManager){
        checkDeviceLiveData.value =
                biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS && preferences.getStringByKey(
                AppPreferences.EMAIL
            ) != null
    }

}