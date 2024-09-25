package com.example.note.ui.theme.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

//this is not database, we are saying our database will have .. things
@Entity(tableName = "Notes_tbl")//entity means this is our table  with columns ...
data class Note(
    @PrimaryKey
    val id:UUID= UUID.randomUUID(), // each time a note is created it will asign random id's


    @ColumnInfo(name = "note_title")
    val title:String,

    @ColumnInfo(name = "note_description")
    val description:String,

    @ColumnInfo(name = "note_entry_date")
    val entryDate: Date =  Date.from(Instant.now())// time when note was created
)
