package com.miin.learning.memorynote.framework.di

import com.miin.learning.memorynote.framework.ListViewModel
import com.miin.learning.memorynote.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}
