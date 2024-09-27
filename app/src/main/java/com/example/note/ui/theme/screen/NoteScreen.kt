package com.example.note.ui.theme.screen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.note.navigation.Routing
import com.example.note.ui.theme.model.Note
import com.example.note.util_typeconv.formatDate
import com.example.note.R

//to know whether user is scrolling up or down
@Composable
private fun LazyListState.isScrollingUp():Boolean{

        /**
         * previousIndex: Tracks the position (index) of the
         * first visible item in the LazyColumn during the last frame.

         * previousScrollOffset: Tracks the pixel offset within the first visible i
         * tem during the last frame (since scrolling is not always by full items, but also in between items).
         */
        var previousIndex by remember (this) {
        mutableStateOf(firstVisibleItemIndex) //firstVisibleitem index is the current positon
    }
    var previousScrollOffset by remember (this){
        mutableStateOf(firstVisibleItemScrollOffset)
    }
    return remember(this){
        derivedStateOf {
            if(previousIndex != firstVisibleItemIndex){
                previousIndex > firstVisibleItemIndex
            }
            else{
                previousScrollOffset>=firstVisibleItemScrollOffset
            }.also {
                previousIndex =firstVisibleItemIndex
                previousScrollOffset=firstVisibleItemScrollOffset
            }
        }
    }.value//user scrolls upward then value is true , if downward then false
}


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



    //creating state to know when the user is scrolling or not
    val listState= rememberLazyListState()



    //making state for alert dialouge
     var showDeleteDialouge = remember {
         mutableStateOf(false)
     }
    //making state for selected note
    var SelectNote:Note? by remember {
        mutableStateOf(null)
    }

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
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Edit, "kei na kei ta lekhum") },
                text = { Text(text = "Kei Na Kei Ta Lekhum",
                       style = TextStyle(
                           fontSize = 15.sp,
                           fontWeight = FontWeight.Bold)
                       ) },
                containerColor = MaterialTheme.colorScheme.primary,
                expanded =  listState.isScrollingUp(),
                onClick = { navController.navigate(Routing.AddnoteScreen.name) },
            )
        }
    ) { innerpadding ->
            LazyColumn(modifier = Modifier
                .padding(innerpadding)
                .padding(7.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                state = listState){
                items(notes){
                    NoteRow(note = it, onNoteClicked = {
                        //onRemoveNote(it)
                        SelectNote=it
                        showDeleteDialouge.value= true
                    },
                        onNoteLongPressed ={
                            //edit on longpress
                            navController.navigate(route = Routing.AddnoteScreen.name+"/${it.id}")
                        } )
                }
            }
        if(showDeleteDialouge.value && SelectNote!=null){
            CustomDialogExample(
                icon = Icons.Default.Warning,
                dialogTitle="Delete Note",
                dialogText = "Will you delete me? 🥹😭🤧 Are you sure? Please think again 😭",
                Confirmation ={
                    onRemoveNote(SelectNote!!)
                    showDeleteDialouge.value=false
                },
                onDismissRequest={
                    showDeleteDialouge.value =false
                }
            )
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

