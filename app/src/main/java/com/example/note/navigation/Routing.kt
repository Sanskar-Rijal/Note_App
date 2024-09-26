package com.example.note.navigation

enum class Routing{

    AddnoteScreen,
    Verification,
    NoteScreen;
    companion object{
        fun fromRoute(route:String?):Routing
        =when(route?.substringBefore("/")){
            NoteScreen.name->NoteScreen
            AddnoteScreen.name->AddnoteScreen
            Verification.name->Verification
            null->NoteScreen
            else->throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
