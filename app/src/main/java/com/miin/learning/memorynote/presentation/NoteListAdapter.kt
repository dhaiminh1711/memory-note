package com.miin.learning.memorynote.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miin.learning.core.data.Note
import com.miin.learning.memorynote.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class NoteListAdapter(var notes: ArrayList<Note>) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    private lateinit var binding: ItemNoteBinding

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    inner class NoteViewHolder(itemView: ItemNoteBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val layout = itemView.noteLayout
        private val noteTitle = itemView.title
        private val noteContent = itemView.content
        private val noteDate = itemView.date

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content

            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last updated: ${sdf.format(resultDate)}"
        }
    }
}
