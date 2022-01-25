package com.miin.learning.core.usecase

import com.miin.learning.core.data.Note
import com.miin.learning.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}
