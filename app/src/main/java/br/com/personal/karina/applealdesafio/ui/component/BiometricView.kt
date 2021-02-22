package br.com.personal.karina.applealdesafio.ui.component

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.personal.karina.applealdesafio.R



class BiometricView(

    private val fragment: Fragment
) {
    private lateinit var biometricPrompt: BiometricPrompt
    private val executor = ContextCompat.getMainExecutor(fragment.requireContext())


    private fun buildDialogView(): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(fragment.requireContext().getString(R.string.message_title_biometric))
            .setDescription(
                fragment.requireContext().getString(R.string.message_description_biometric_dialog)
            )
            .setNegativeButtonText(
                fragment.requireContext().getString(R.string.message_enter_login_password_not_save)
            )
            .build()

    fun showDialogBiometric(successCallBack: () -> Unit,
                            errorCallback: (() -> Unit)? = null){
        biometricPrompt = BiometricPrompt(fragment, executor, object :
            BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                errorCallback?.invoke()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                successCallBack.invoke()
            }
        })
        biometricPrompt.authenticate(buildDialogView())
    }



}
