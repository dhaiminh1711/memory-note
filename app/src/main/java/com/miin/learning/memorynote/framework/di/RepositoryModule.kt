package com.miin.learning.memorynote.framework.di

import android.app.Application
import com.miin.learning.core.repository.NoteRepository
import com.miin.learning.memorynote.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}
