package com.example.note

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.note.manager.BiometricPromptManager
import com.example.note.navigation.NotesNavigation
import com.example.note.ui.theme.NoteTheme
import com.example.note.ui.theme.screen.NoteViewModel
import com.example.note.ui.theme.screen.SplashModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashModel>()

    //for biometric setup
    private val promptManager by lazy{
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //settiing up splash screen
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                !viewModel.isReady.value
                //as long as it's ture splash screen will be shown
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator=OvershootInterpolator()
                zoomX.duration=500L
                zoomX.doOnEnd { screen.remove() }

                //for Y value
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator=OvershootInterpolator()
                zoomY.duration=500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        //enableEdgeToEdge()
        setContent {
            //saving notes
//            val notes = remember{
//                mutableStateListOf<Note>()
//            }
            Myapp {
                val noteViewModel:NoteViewModel by viewModels()
                //you can use like this also
                //val noteViewModel =viewModel<NoteViewModel>()
                NotesNavigation(noteViewModel,promptManager)
            }
        }
    }
}





@Composable
fun Myapp(content: @Composable () ->Unit )
{
    NoteTheme {
        content()
    }
}



/*
//using view models
@Composable
fun NotesApp(noteViewModel: NoteViewModel= viewModel()){

    //getting all our notes
    val notesList =noteViewModel.noteList.collectAsState().value
    //in flow you always have to collect the information


    NoteScreen(notes =notesList ,
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
*/
//done