package com.miin.learning.memorynote.framework

import com.miin.learning.core.usecase.AddNote
import com.miin.learning.core.usecase.GetAllNotes
import com.miin.learning.core.usecase.GetNote
import com.miin.learning.core.usecase.GetWordCount
import com.miin.learning.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val getWordCount: GetWordCount,
)
