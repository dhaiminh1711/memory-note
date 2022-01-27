package com.miin.learning.memorynote.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.miin.learning.core.data.Note
import com.miin.learning.memorynote.R
import com.miin.learning.memorynote.databinding.FragmentNoteBinding
import com.miin.learning.memorynote.framework.NoteViewModel

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)
    private var noteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteNote -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete note")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.deleteNote(currentNote)
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .create()
                        .show()
                }
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        binding.apply {
            saveBtn.setOnClickListener {
                if (titleView.text.toString() != "" || contentView.text.toString() != "") {
                    val time = System.currentTimeMillis()
                    currentNote.apply {
                        title = titleView.text.toString()
                        content = contentView.text.toString()
                        updateTime = time
                        if (id == 0L) {
                            creationTime = time
                        }
                        viewModel.savedNote(currentNote)
                    }
                } else {
                    Navigation.findNavController(it).popBackStack()
                }
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(binding.titleView).popBackStack()
            } else {
                Toast.makeText(context,
                    "Something went wrong, please try again",
                    Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            note?.let {
                currentNote = it
                binding.apply {
                    titleView.setText(it.title, TextView.BufferType.EDITABLE)
                    contentView.setText(it.content, TextView.BufferType.EDITABLE)
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.titleView.windowToken, 0)
    }

    companion object {
        fun newInstance() = NoteFragment()
    }
}
