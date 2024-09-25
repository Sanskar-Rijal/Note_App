package com.example.note.util_typeconv

import androidx.room.TypeConverter
import java.util.UUID

class UUIDconverter {

    @TypeConverter
    fun FromUUID(uuid: UUID):String{
        return uuid.toString()
    }

    @TypeConverter
        fun uuidFromString(string: String):UUID{
            return UUID.fromString(string)
        }

}