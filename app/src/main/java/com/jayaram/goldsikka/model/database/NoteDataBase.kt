package com.jayaram.goldsikka.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jayaram.goldsikka.model.Dto.NoteEntity
import com.jayaram.goldsikka.model.dao.NoteDao

@Database(entities = [NoteEntity::class], version = 2, exportSchema = true)
abstract class NoteDataBase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}