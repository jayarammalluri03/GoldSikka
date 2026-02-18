package com.jayaram.goldsikka.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jayaram.goldsikka.R
import com.jayaram.goldsikka.databinding.HomeFragmentBinding
import com.jayaram.goldsikka.model.Dto.NoteEntity
import com.jayaram.goldsikka.ui.adapter.NotesAdapter
import com.jayaram.goldsikka.ui.viewmodels.HomeViewModel
import com.jayaram.goldsikka.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment: Fragment(){
    private var _binding: HomeFragmentBinding? = null

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = NotesAdapter(
        onEditClick = {
            val action = HomeFragmentDirections.actionHomeFragment2ToAddEditFragment(it.id ?: -1)
            findNavController().navigate(action)
        }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeData()
        _binding?.etSearch?.addTextChangedListener {
            viewModel.updateSearchQuery(it.toString())
        }
        _binding?.addIcon?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToAddEditFragment(-1)
            findNavController().navigate(action)
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note = adapter.currentList[position]
                    viewModel.deleteNote(note)
                }
            }
        ItemTouchHelper(itemTouchHelperCallback)
            .attachToRecyclerView(_binding?.recyclerNotes)
    }

    private fun setupRecycler() {
        val spanCount = resources.getInteger(R.integer.note_grid_span)
        _binding?.recyclerNotes?.layoutManager = GridLayoutManager(requireContext(), spanCount)
        _binding?.recyclerNotes?.adapter = adapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notesState.collect { state ->

                    when(state){
                        is UiState.Error -> {
                            _binding?.noNotesFoundTxt?.visibility= View.VISIBLE
                            _binding?.noNotesFoundTxt?.text= state.message
                        }
                        UiState.Loading -> {

                        }
                        is UiState.Success<List<NoteEntity>> -> {
                            if(state.data.isNotEmpty()){
                                _binding?.noNotesFoundTxt?.visibility = View.GONE
                                _binding?.recyclerNotes?.visibility= View.VISIBLE
                                adapter.submitList(state.data)
                            }else {
                                _binding?.noNotesFoundTxt?.visibility= View.VISIBLE
                                _binding?.recyclerNotes?.visibility= View.GONE
                                _binding?.noNotesFoundTxt?.text = " No List to Show"
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}