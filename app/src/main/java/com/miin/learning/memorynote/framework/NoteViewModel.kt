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
import com.miin.learning.memorynote.framework.di.ApplicationModule
import com.miin.learning.memorynote.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean>
        get() = _saved

    private val _currentNote = MutableLiveData<Note?>()
    val currentNote: LiveData<Note?>
        get() = _currentNote

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }

    fun savedNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            _saved.postValue(true)
        }
    }

    fun getNote(id: Long) {
        coroutineScope.launch {
            val note = useCases.getNote(id)
            _currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNote(note)
            _saved.postValue(true)
        }
    }
}
