package com.jayaram.goldsikka.model.repository

import com.jayaram.goldsikka.model.Dto.NoteEntity
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    fun getNotes(): Flow<List<NoteEntity>>

    fun getNotesByTitle(id: Int): Flow<NoteEntity>

    suspend fun insertNote(note: NoteEntity)

    suspend fun deleteNote(note: NoteEntity)

    fun searchNotes(searchQuery: String): Flow<List<NoteEntity>>

    suspend fun updateNote(note: NoteEntity)
}