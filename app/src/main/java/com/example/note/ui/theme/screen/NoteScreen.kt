package com.example.note.ui.theme.screen
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.note.R
import com.example.note.components.Description
import com.example.note.components.NoteInputText
import com.example.note.components.button
import com.example.note.navigation.Routing
import com.example.note.ui.theme.data.NoteDataSource
import com.example.note.ui.theme.model.Note
import com.example.note.util_typeconv.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    notes:List<Note>,
    onAddNote:(Note)->Unit,
    onRemoveNote:(Note)->Unit,
    onUpdaetNote:(Note)->Unit
) {

    //getting context for  using toast
    val context = LocalContext.current



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
//                actions = {
//                    Icon(
//                        imageVector = Icons.Rounded.Notifications,
//                        contentDescription = "Icon",
//                        tint = Color.Unspecified
//                    )
//                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate(Routing.AddnoteScreen.name)
            },
                icon = { Icon(Icons.Filled.Edit, "kei na kei ta lekhum") },
                text = { Text(text = "Kei Na Kei Ta Lekhum",
                       style = TextStyle(
                           fontSize = 15.sp,
                           fontWeight = FontWeight.Bold)
                       ) },
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerpadding ->
            LazyColumn(modifier = Modifier
                .padding(innerpadding)
                .padding(7.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
                horizontalAlignment = Alignment.Start) {
                items(notes){
                    NoteRow(note = it, onNoteClicked = {
                        onRemoveNote(it)
                    },
                        onNoteLongPressed ={
                            //edit on longpress
                            navController.navigate(route = Routing.AddnoteScreen.name+"/${it.id}")
                        } )
                }
            }
        }
        //making scrollable list for showing saved notes
    }


//@Preview(showBackground = true)
//@Composable
//fun NoteScreenPreview() {
//    NoteScreen(notes =NoteDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {}, onUpdaetNote = {})
//}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteRow(modifier:Modifier=Modifier,
            note:Note,
            onNoteClicked:(Note)->Unit,
            onNoteLongPressed:(Note)->Unit){

    Card (modifier = Modifier
        .padding(7.dp)
        .clip(RoundedCornerShape(
            topEnd = 33.dp,
            bottomStart = 33.dp))
        .fillMaxWidth()
        //setting click events
        .combinedClickable(
            //deleting note
            onClick = {
                onNoteClicked(note)
            },
            //editing note
            onLongClick = {
                onNoteLongPressed(note)
            }
        )
        ,
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
    ) {

        Column(modifier=Modifier
            .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(text = note.title,
                modifier=Modifier.padding(bottom = 5.dp),
                color =MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontStyle = FontStyle.Normal,
                    fontSize = 25.sp
                ))
            Text(text=note.description ,
                color = MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontStyle = FontStyle.Normal,
                    fontSize = 15.sp
                ))
            Box(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                , contentAlignment = Alignment.TopEnd)
            {
                Text(
                    text = formatDate(note.entryDate.time),
                    style = MaterialTheme.typography.bodySmall
                    //color = Color.Black
                )
            }
        }
    }
}