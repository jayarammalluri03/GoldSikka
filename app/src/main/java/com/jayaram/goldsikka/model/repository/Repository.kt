package com.jayaram.goldsikka.model.repository

import com.jayaram.goldsikka.model.dao.NoteDao
import javax.inject.Inject


import com.jayaram.goldsikka.model.Dto.NoteEntity
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val noteDao: NoteDao
): RepositoryInterface {

    override fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getNotes()
    }

    override  fun getNotesByTitle(id: Int): Flow<NoteEntity> {
        return noteDao.getNotesByTitle(id)
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

   override fun searchNotes(searchQuery: String): Flow<List<NoteEntity>>{
       return noteDao.searchNotes(searchQuery)
   }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }


}
