package com.example.note.repository

import com.example.note.ui.theme.data.NoteDatabaseDao
import com.example.note.ui.theme.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository@Inject constructor(private val notedatabaseDao:NoteDatabaseDao) {
    suspend fun addNote(note:Note)=notedatabaseDao.Insert(note)
    suspend fun updateNote(note:Note)=notedatabaseDao.Update(note)
    suspend fun deleteNote(note:Note)=notedatabaseDao.deleteNote(note)
    suspend fun deleteAllnote() = notedatabaseDao.DeleteAll()
    fun getAllnote():Flow<List<Note>> = notedatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()

}