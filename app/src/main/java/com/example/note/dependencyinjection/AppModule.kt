package com.example.note.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.note.ui.theme.data.NoteDataBase
import com.example.note.ui.theme.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//creating room data base
@Module
@InstallIn(SingletonComponent::class) //it means we don't want to create database again and agaain when the app starts
object AppModule {

    @Singleton //we want to create one instance of this particular item
    @Provides //this is something that provies something to entire route
    fun provideNotesDao(noteDatabase:NoteDataBase):NoteDatabaseDao
    =noteDatabase.noteDao()


    @Singleton
    @Provides//creating actual database

    //to create database we need application context
    // (context means it knows every thing about the app , it has access to every thing)
    fun provideAppDataBase(@ApplicationContext context: Context):NoteDataBase
            =Room.databaseBuilder(
        context,
        NoteDataBase::class.java,
        "notes_db")//name of database
        .fallbackToDestructiveMigrationFrom()
        .build()



}