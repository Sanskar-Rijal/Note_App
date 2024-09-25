package com.example.note.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.note.components.Description
import com.example.note.components.NoteInputText
import com.example.note.components.button
import com.example.note.navigation.Routing
import com.example.note.ui.theme.model.Note
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddnoteScreen(
    navController:NavController,
    note: UUID?,
    noteViewModel:NoteViewModel ) {


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Kei Na Kei Ta Lekhum") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White),
                navigationIcon = {
                    Row(modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.Start) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack icon",
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            })

                    }
                }
            )
        }


    )

    {innerpadding->

        val check: List<Note> = noteViewModel.getallnote().filter { smth ->
            smth.id == note
        }
        val currentNote: Note? = check.firstOrNull()
        //since the value is changing in text field so we have to save it using a state
        val RemTitle = remember {
            mutableStateOf(currentNote?.title ?: "")
        }

        //state for description
        val remDescription = remember {
            mutableStateOf(currentNote?.description ?: "")
        }

//    /**
//     * state for checking if note is being edited
//     */
//    val editingNote= remember {
//        mutableStateOf<Note?>(null)
//    }

        //hiding keyboard after user pressed save button
        val keyboardController = LocalSoftwareKeyboardController.current

        //getting context for  using toast
        val context = LocalContext.current


        Column(
            modifier = Modifier
                .padding(innerpadding)
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Column(modifier = Modifier.padding(30.dp)) {

                //creating text fields for title

                NoteInputText(modifier = Modifier.padding(
                    top = 9.dp,
                    bottom = 8.dp
                )
                    .fillMaxWidth(),
                    Text = RemTitle.value,
                    label = "Title",
                    onTextChange = { Txt ->
                        //get is the text that is extracted when we enter text in the text box
                        if (Txt.all { char ->
                                //char is the each character that we are passing get.all gives each characte in char
                                char.isLetter() || char.isWhitespace() || char.isDefined()
                            })
                        //if "if" statement is true then
                            RemTitle.value = Txt
                    })

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                //creating text fields for description

                Description(modifier = Modifier.padding(
                    top = 9.dp,
                    bottom = 8.dp
                )
                    .fillMaxWidth(),
                    Text = remDescription.value,
                    label = "Description", onTextChange = { Txt ->
                        if (Txt.all { char ->
                                char.isLetter() || char.isWhitespace() || char.isDefined()
                            })
                            remDescription.value = Txt
                    })
                Spacer(modifier = Modifier.height(20.dp))
            }

            //save Button

            button(text = if (currentNote == null) "SAVE" else "UPDATE",
                onclick = {
                    if (RemTitle.value.isNotEmpty() && remDescription.value.isNotEmpty()) {

                        //for editing note
                        if (currentNote == null) {
                            //add a new note
                            //save or add to list
                            noteViewModel.addNote(
                                Note(
                                    title = RemTitle.value,
                                    description = remDescription.value
                                )
                            )
                        } else {
                            noteViewModel.updateNote(
                                currentNote.copy(
                                    title = RemTitle.value,
                                    description = remDescription.value
                                )
                            )
                            //update existing note

                        }
                        //clear the text fields after saving
                        RemTitle.value = ""
                        remDescription.value = ""

                        keyboardController?.hide()

                        Toast.makeText(
                            context,
                            "kei na kei ta added, successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        navController.navigate(Routing.NoteScreen.name)
                    } else {
                        Toast.makeText(context, "Enter Title and Description ", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            HorizontalDivider(modifier = Modifier.padding(10.dp))

        }
    }
}