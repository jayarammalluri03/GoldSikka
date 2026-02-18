package com.jayaram.goldsikka.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.jayaram.goldsikka.model.Dto.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity")
    fun getNotes(): Flow<List<NoteEntity>>


    @Query("SELECT * FROM NoteEntity WHERE id = :id")
     fun getNotesByTitle(id: Int): Flow<NoteEntity>

    @Query(""" SELECT * FROM NoteEntity WHERE title LIKE :searchQuery || '%'""")
    fun searchNotes(searchQuery: String): Flow<List<NoteEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

}