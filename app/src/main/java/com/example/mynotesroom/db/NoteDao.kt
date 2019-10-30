package com.example.mynotesroom.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note:Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNote() : List<Note>

    @Insert
    suspend fun addMultipleNotes(vararg note:Note)

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)
}