package com.jayaram.goldsikka.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayaram.goldsikka.model.Dto.NoteEntity
import com.jayaram.goldsikka.model.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AddEditViewModel @Inject constructor(val repositoryInterface: RepositoryInterface): ViewModel() {

    private val _noteId = MutableStateFlow<Int?>(null)
    val noteId: StateFlow<Int?> = _noteId


    private val _showToast = MutableStateFlow<Boolean>(false)
    val showToast: StateFlow<Boolean> = _showToast

    val title = MutableStateFlow("")
    val description = MutableStateFlow("")


    private fun loadNote(id: Int) {
        viewModelScope.launch {
            if(id > -1){
                repositoryInterface.getNotesByTitle(id).collect { note ->
                    title.value = note.title
                    description.value = note.description
                }
            }
        }
    }
    fun setNoteId(id: Int?) {
        _noteId.value = id
        if (id != null) {
            loadNote(id)
        }
    }

    fun updateTitle(text: String) {
        title.value = text
    }

    fun updateDescription(text: String) {
        description.value = text
    }


    fun saveData(): Flow<Boolean> = flow {

        if (title.value.isEmpty() || description.value.isEmpty()) {
            _showToast.emit(true)
            delay(3000)
            _showToast.emit(false)
            emit(false)
        } else {
            withContext(Dispatchers.IO) {
                if ((_noteId.value ?: -1) > -1) {
                    repositoryInterface.updateNote(
                        NoteEntity(
                            id = _noteId.value,
                            title = title.value,
                            description = description.value,
                            createAt = System.currentTimeMillis()
                        )
                    )
                } else {
                    repositoryInterface.insertNote(
                        NoteEntity(
                            id = null,
                            title = title.value,
                            description = description.value,
                            createAt = System.currentTimeMillis()
                        )
                    )
                }
            }
            emit(true)
        }
    }



}