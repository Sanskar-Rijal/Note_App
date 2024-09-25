package com.example.note.navigation

enum class Routing{

    AddnoteScreen,
    NoteScreen;
    companion object{
        fun fromRoute(route:String?):Routing
        =when(route?.substringBefore("/")){
            NoteScreen.name->NoteScreen
            AddnoteScreen.name->AddnoteScreen
            null->NoteScreen
            else->throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}