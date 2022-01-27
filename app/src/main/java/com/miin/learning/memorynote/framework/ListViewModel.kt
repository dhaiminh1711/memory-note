package com.miin.learning.memorynote.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miin.learning.core.data.Note
import com.miin.learning.core.repository.NoteRepository
import com.miin.learning.core.usecase.AddNote
import com.miin.learning.core.usecase.GetAllNotes
import com.miin.learning.core.usecase.GetNote
import com.miin.learning.core.usecase.RemoveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
    )

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>>
        get() = _notes

    fun getNotes() {
        coroutineScope.launch {
            val noteList = useCases.getAllNotes()
            _notes.postValue(noteList)
        }
    }
}
