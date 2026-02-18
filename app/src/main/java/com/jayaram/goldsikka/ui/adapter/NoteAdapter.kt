package com.jayaram.goldsikka.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jayaram.goldsikka.databinding.NoteCardviewBinding
import com.jayaram.goldsikka.model.Dto.NoteEntity
import com.jayaram.goldsikka.utils.Common

class NotesAdapter(private val onEditClick: (NoteEntity) -> Unit) :
    ListAdapter<NoteEntity, NotesAdapter.NoteViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesAdapter.NoteViewHolder {
        val binding = NoteCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        private val binding: NoteCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteEntity) {
            binding.tvTitle.text = note.title
            binding.tvDescription.text = note.description
            binding.tvCreatedAt.text = "Created At ${Common.getDate(note.createAt)}"
            binding.ivEdit.setOnClickListener {
                onEditClick(note)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem == newItem
    }
}