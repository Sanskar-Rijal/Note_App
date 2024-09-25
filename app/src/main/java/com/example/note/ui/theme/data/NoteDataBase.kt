package com.example.note.ui.theme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.note.ui.theme.model.Note
import com.example.note.util_typeconv.DateConverter
import com.example.note.util_typeconv.UUIDconverter


//this is not actual data base, it's a structure of database,skelaton
@Database(entities=[Note::class],version=1, exportSchema = false)

@TypeConverters(DateConverter::class,UUIDconverter::class)
abstract class NoteDataBase:RoomDatabase() {

    abstract fun noteDao():NoteDatabaseDao
}