package com.example.mynotesroom.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesroom.R
import com.example.mynotesroom.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(private val notes : List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        holder.view.text_view_title.text = notes[position].title
        holder.view.text_view_note.text = notes[position].note

        holder.view.setOnClickListener {

            val actions = HomeFragmentDirections.actionAddNote()
            actions.note = notes[position]
            Navigation.findNavController(it).navigate(actions)

        }

    }


    class NoteViewHolder(val view:View) : RecyclerView.ViewHolder(view)
}