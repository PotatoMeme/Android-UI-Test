package com.potatomeme.chirang_note_app.presentation_xml.main

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

import com.potatomeme.chirang_note_app.presentation_xml.databinding.ItemContainerNoteBinding
import com.potatomeme.chirang_note_app.presentation_xml.model.Note

class NotesAdapter(private val onItemClick: (note: Note, position: Int) -> Unit) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(diffCallback) {
    inner class NoteViewHolder(private val binding: ItemContainerNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.textTitle.text = note.title
            if (note.subtitle?.trim()?.isEmpty() != false) {
                binding.textSubtitle.visibility = View.GONE
            } else {
                binding.textSubtitle.text = note.subtitle
            }
            binding.textDateTime.text = note.dateTime

            binding.layoutNote.background = GradientDrawable().apply {
                setColor(
                    Color.parseColor(
                        note.color ?: "#333333"
                    )
                )
            }
            binding.imageNote.apply {
                if (note.imagePath != null) {
                    visibility = View.VISIBLE
                    setImageBitmap(BitmapFactory.decodeFile(note.imagePath))
                    setImageURI(note.imagePath.toUri())
                } else {
                    visibility = View.GONE
                }
            }
            binding.layoutNote.setOnClickListener {
                onItemClick(note, adapterPosition)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemContainerNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}