package com.example.mynotesroom.ui


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import com.example.mynotesroom.R
import com.example.mynotesroom.Utils.toast
import com.example.mynotesroom.db.Note
import com.example.mynotesroom.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    private var note : Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {


        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            note = AddNoteFragmentArgs.fromBundle(it).note
            edit_text_title.setText(note?.title)
            edit_text_note.setText(note?.note)

        }

        button_save.setOnClickListener { view ->

            val noteTitle = edit_text_title.text.toString().trim()
            val noteBody = edit_text_note.text.toString().trim()

            if(noteTitle.isNullOrEmpty()){
                edit_text_title.error = "title required"
                edit_text_title.requestFocus()
                return@setOnClickListener
            }
            if(noteBody.isNullOrEmpty())
            {
                edit_text_note.error = "note required"
                edit_text_note.requestFocus()
                return@setOnClickListener
            }

            /*
                In order to save data into the ROOM database we cannot save it using the main THREAD
                So we use AsyncTask in the background thread to use the room database.

                OR use Coroutines
             */

            //saveNote(note)

            launch {

                context?.let {

                    val mNote = Note(noteTitle,noteBody)

                        if(note == null)
                        {
                            NoteDatabase(it).getNoteDao().addNote(mNote)
                            it.toast("Note Saved!")
                        }
                        else
                        {
                            mNote.id = note!!.id
                            NoteDatabase(it).getNoteDao().updateNote(mNote)
                            it.toast("Note Updated!")
                        }

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }

        }
    }

    private fun deleteNote()
    {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes"){_,_ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No"){_,_ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.delete -> if(note != null)
            {
                deleteNote()
            }
            else
            {
                context?.toast("Cannot delete note that haven't been created!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu,menu)
    }

    /*
        This is using Async to perform background thread call to save the note into ROOM database.
     */

//    private fun saveNote(note:Note){
//
//        class SaveNote : AsyncTask<Void,Void,Void>()
//        {
//            override fun doInBackground(vararg p0: Void?): Void? {
//                NoteDatabase(activity!!).getNoteDao().addNote(note)
//                return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//
//                Toast.makeText(activity,"Note Saved",Toast.LENGTH_LONG).show()
//            }
//
//        }
//
//        SaveNote().execute()
//    }


}
