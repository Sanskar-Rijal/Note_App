package com.example.note.ui.theme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.note.ui.theme.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from notes_tbl") //* means all , i mean select all elements form notes tbl
     fun getNotes():Flow<List<Note>>
        //it will select all notes from notes_tbl and return list of notes



    @Query("SELECT * from notes_tbl where id=:id")  //where id=:id means id that we are going to pass
    suspend fun getNoteById(id:String):Note



    //now we want to insert something
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(note:Note)

//update something on our database
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Update(note:Note)


    //we want to delete everything grom our database
    @Query("DELETE from notes_tbl")
    suspend fun DeleteAll()

    //delete something from database
    @Delete
    suspend fun deleteNote(note:Note)


}
