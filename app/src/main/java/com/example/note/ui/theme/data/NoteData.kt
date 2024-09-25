package com.example.note.ui.theme.data

import com.example.note.ui.theme.model.Note


class NoteDataSource {
    fun loadNotes():List<Note> {
        return listOf(
            Note(title ="Delete", description = "If you tap on the saved notes then it will be deleted"),
            Note(title="EDIT", description = "If you want to edit the saved notes, just long press the note 😎"),
            Note(title= "Security", description = "Secured by AI✅,AI==APRIL's INTELLIGENCE"),
            Note(title = "Oops", description = "Notes didn't get deleted after you pressed😭,just add a new note i'll be gone🥹")
        )
    }
}