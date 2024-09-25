package com.example.note.manager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BiometricPromptManager (
    private  val activity: AppCompatActivity
    ) {
        //creating channel to send the result to mainactivity
        private val resultChannel = Channel<BiometricResult>()
        val promptResult =resultChannel.receiveAsFlow()

        fun showBiometricPrompt(
            title:String,
            description:String
        ){
            val manager: BiometricManager = BiometricManager.from(activity)

            //val authenticator = BIOMETRIC_STRONG or DEVICE_CREDENTIAL
            //device credental allows user to choose device pattern over fingerprint , this is available on if api level is greater than 13 so we check first
            val authenticator = if(Build.VERSION.SDK_INT>=30){
                BIOMETRIC_STRONG or   DEVICE_CREDENTIAL
            }
            else{
                BIOMETRIC_STRONG
            }

            val promptInfo= PromptInfo.Builder()
                .setTitle(title)
                .setDescription(description)
                .setAllowedAuthenticators(authenticator)
            if(Build.VERSION.SDK_INT<30){
                promptInfo.setNegativeButtonText("Cancel")
            }

            when(manager.canAuthenticate(authenticator)){
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                    resultChannel.trySend(BiometricResult.HardwareUnavailable)
                    return
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                    resultChannel.trySend(BiometricResult.FeatureUnavailable)
                    return
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                    resultChannel.trySend(BiometricResult.AuthenticationNoteSet)
                    return
                }
                else -> Unit
            }

            val prompt = BiometricPrompt(
                activity,
                object : BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        resultChannel.trySend(BiometricResult.AuthenticationError(errorCode.toString()))
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        resultChannel.trySend(BiometricResult.AuthenticationFailed)
                    }
                }
            )
            prompt.authenticate(promptInfo.build())
        }
        //if user don't have fingerprint , or if it's being used by someother appps then
        sealed interface BiometricResult {
            data object HardwareUnavailable :BiometricResult

            data object FeatureUnavailable :BiometricResult

            data class AuthenticationError(val error:String):BiometricResult

            data object AuthenticationFailed:BiometricResult

            data object AuthenticationSuccess:BiometricResult

            data object AuthenticationNoteSet:BiometricResult
        }
}