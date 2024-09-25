package com.example.note.ui.theme.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.repository.NoteRepository
import com.example.note.ui.theme.data.NoteDataSource
import com.example.note.ui.theme.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * we inject our repository to viewmodel
 */
@HiltViewModel
class NoteViewModel @Inject constructor(private val repository:NoteRepository) :ViewModel() {//inherit from View model
    //private var noteList = mutableStateListOf<Note>()

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList=_noteList.asStateFlow()
    init {
        //noteList.addAll(NoteDataSource().loadNotes())
        viewModelScope.launch(Dispatchers.IO){//we are making sure our highway have much more lanes
            //so that more cars can run  so we used Dispatchers.IO
            repository.getAllnote()
                .distinctUntilChanged()
                .collect{listofnotes->
                    if(listofnotes.isNullOrEmpty()){
                        _noteList.value= NoteDataSource().loadNotes()
                    }
                    else{
                        _noteList.value=listofnotes
                    }
                }
        }
    }

    //adding note
    /**
     * Using viewModelScope.launch in the addNote function is important because it allows you
     * to run the code in a coroutine that is tied to the lifecycle of the ViewModel.
     * viewModelScope is provided by Jetpack and is specifically tied to the lifecycle of the ViewModel.
     * When the ViewModel is cleared (e.g., when the UI is no longer needed or destroyed),
     * any coroutines launched in viewModelScope are automatically canceled.
     * This prevents memory leaks and ensures that long-running operations are stopped
     * when no longer needed.
     *
     * Repository operations like addNote(note) are typically database or network calls that
     * may take time to complete. Since these operations are potentially long-running, they
     * need to be executed in the background. launch creates a new coroutine, allowing the
     * database operation to run asynchronously without blocking the main thread, which is
     * crucial for maintaining a responsive UI.
     *
     * addNote(note) function is marked as suspend, which means it can only be called from within a
     * coroutine. Using viewModelScope.launch creates the coroutine context required to call suspending
     * functions.
     */
    fun addNote(note:Note) = viewModelScope.launch { //we don't use suspend because viewModelScope will take care
       repository.addNote(note)
    }

     fun updateNote(note:Note)=viewModelScope.launch {
        repository.updateNote(note)
    }

     fun removeNote(note:Note)=viewModelScope.launch {
        repository.deleteNote(note)
    }

 fun getallnote():List<Note> {
     return _noteList.value
 }
}