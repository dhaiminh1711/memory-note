package com.miin.learning.memorynote.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.miin.learning.memorynote.databinding.FragmentListBinding
import com.miin.learning.memorynote.framework.ListViewModel

class ListFragment : Fragment(), ListAction {
    private lateinit var binding: FragmentListBinding
    private val notesListAdapter = NoteListAdapter(arrayListOf(), this)
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            noteListView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = notesListAdapter
            }
            addNoteBtn.setOnClickListener { goToNoteDetails() }
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            binding.apply {
                loadingView.visibility = View.GONE
                noteListView.visibility = View.VISIBLE
                notesListAdapter.updateNotes(notes.sortedByDescending { it.updateTime })
            }
        }
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action = ListFragmentDirections.actionGoToNote(id)
        Navigation.findNavController(binding.noteListView).navigate(action)
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}
