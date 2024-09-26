package com.example.note.ui.theme.screen

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.note.manager.BiometricPromptManager
import com.example.note.navigation.Routing
import com.example.note.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun failed(navController:NavController,promptManager: BiometricPromptManager) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
            )
        }) {innerpadding->
        val biometricResult by promptManager.promptResult
            .collectAsState(initial = null)

        val enrollLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult() ,
            onResult ={
                println("Activity result is  $it")
            }
        )
        LaunchedEffect(biometricResult) {
            if(biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNoteSet){
                if (Build.VERSION.SDK_INT>30 ){
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or  DEVICE_CREDENTIAL
                        )
                    }
                    enrollLauncher.launch(enrollIntent)
                }
            }
        }
        Column(modifier=Modifier
            .fillMaxSize()
            .padding(innerpadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                promptManager.showBiometricPrompt(
                    title="KEI NA KEI TA HO",
                    description = "SECURED BY APRIL"
                )
            }) {
                Text("Authenticate", style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold
                ))
            }
            if(biometricResult !=null){
                when(biometricResult) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                        Text(text ="Authentication Error")
                    }

                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                        Text(text ="Authentication Failed")
                    }

                    BiometricPromptManager.BiometricResult.AuthenticationNoteSet -> {
                        Text(text ="Authentication Noteset")
                    }

                    BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(Routing.NoteScreen.name) {
                                popUpTo(Routing.Verification.name) { inclusive = true }
                            }
                        }
                    }

                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                        Text(text = "Feature unavailable")
                    }

                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                        Text(text ="Hardware unavailable")
                    }

                    null -> TODO()
                }
            }
        }
    }
}

