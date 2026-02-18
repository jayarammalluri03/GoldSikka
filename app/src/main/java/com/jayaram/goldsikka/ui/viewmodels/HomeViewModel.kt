package com.jayaram.goldsikka.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayaram.goldsikka.model.Dto.NoteEntity
import com.jayaram.goldsikka.model.repository.RepositoryInterface
import com.jayaram.goldsikka.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repositoryInterface: RepositoryInterface): ViewModel() {


    private val _notesState = MutableStateFlow<UiState<List<NoteEntity>>>(UiState.Loading)
    val notesState: StateFlow<UiState<List<NoteEntity>>> = _notesState

    private val searchQuery = MutableStateFlow("")


    init {
        getNotes()
        observeSearch()
    }

    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        repositoryInterface.getNotes()
                    } else {
                        repositoryInterface.searchNotes(query)
                    }
                }
                .catch {
                    _notesState.value = UiState.Error(it.message ?: "Error")
                }
                .collect {
                    _notesState.value = UiState.Success(it)
                }
        }
    }


    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    private fun getNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryInterface.getNotes().catch {
                _notesState.value = UiState.Error(it.message ?: "No List Founds")
            }.collect {
                _notesState.value = UiState.Success(it)
            }
        }
    }


    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repositoryInterface.deleteNote(note)
        }
    }
}