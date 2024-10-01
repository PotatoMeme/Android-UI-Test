package com.potatomeme.chirang_note_app.presentation_xml.main

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.core.net.toUri
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ItemContainerNoteBinding
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote

class NotesAdapter(private val onItemClick: (note: ParcelableNote) -> Unit) :
    ListAdapter<ParcelableNote, NotesAdapter.NoteViewHolder>(diffCallback) {
    inner class NoteViewHolder(private val binding: ItemContainerNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: ParcelableNote) {
            binding.textTitle.text = note.title
            if (note.subtitle?.trim()?.isEmpty() != false) {
                binding.textSubtitle.visibility = View.GONE
            } else {
                binding.textSubtitle.text = note.subtitle
            }
            binding.textDateTime.text = note.dateTime

            (binding.layoutNote.background as GradientDrawable).apply {
                setColor(
                    Color.parseColor(
                        note.color ?: "#333333"
                    )
                )
            }
            binding.imageNote.apply {
                if (note.imagePath != null) {
                    visibility = View.VISIBLE
                    setImageURI(note.imagePath?.toUri())
                } else {
                    visibility = View.GONE
                }
            }
            binding.layoutNote.setOnClickListener {
                onItemClick(note)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ParcelableNote>() {
            override fun areItemsTheSame(
                oldItem: ParcelableNote,
                newItem: ParcelableNote,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ParcelableNote,
                newItem: ParcelableNote,
            ): Boolean {
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