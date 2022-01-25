package com.miin.learning.memorynote.framework

import android.content.Context
import com.miin.learning.core.data.Note
import com.miin.learning.core.repository.NoteDataSource
import com.miin.learning.memorynote.framework.db.DatabaseService
import com.miin.learning.memorynote.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}
