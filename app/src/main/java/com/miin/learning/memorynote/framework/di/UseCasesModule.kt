package com.miin.learning.memorynote.framework.di

import com.miin.learning.core.repository.NoteRepository
import com.miin.learning.core.usecase.AddNote
import com.miin.learning.core.usecase.GetAllNotes
import com.miin.learning.core.usecase.GetNote
import com.miin.learning.core.usecase.GetWordCount
import com.miin.learning.core.usecase.RemoveNote
import com.miin.learning.memorynote.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}
