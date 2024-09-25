package com.example.note.navigation

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note.manager.BiometricPromptManager
import com.example.note.ui.theme.screen.AddnoteScreen
import com.example.note.ui.theme.screen.NoteScreen
import com.example.note.ui.theme.screen.NoteViewModel
import com.example.note.ui.theme.screen.failed
import java.util.UUID

@Composable
fun NotesNavigation (noteViewModel: NoteViewModel = viewModel(), prompt: BiometricPromptManager){

    //getting all our notes
    val notesList =noteViewModel.noteList.collectAsState().value
    //in flow you always have to collect the information

    val navcontroller= rememberNavController()

    NavHost(
        navController = navcontroller,
        startDestination = Routing.Verification.name){
        //making nav graph

        composable(Routing.Verification.name){
            failed(navController = navcontroller, promptManager = prompt)
        }

        composable(Routing.NoteScreen.name) {
            NoteScreen(navcontroller,
                notes =notesList ,
                onAddNote = {
                    noteViewModel.addNote(it)
                },
                onRemoveNote = {
                    noteViewModel.removeNote(it)
                },
                onUpdaetNote = {
                    noteViewModel.updateNote(it)
                })
        }

        composable(Routing.AddnoteScreen.name){
            AddnoteScreen(navcontroller,null,noteViewModel)
        }

        composable(Routing.AddnoteScreen.name+"/{note}",
            arguments= listOf(navArgument(name="note"){type= NavType.StringType})
        ) {backstackentry->
            val noteId=backstackentry.arguments?.getString("note")
            val uuid = noteId?.let {
                UUID.fromString(it) //converting back to UUID
            }
            AddnoteScreen(navcontroller,uuid,noteViewModel)
        }
    }
}

