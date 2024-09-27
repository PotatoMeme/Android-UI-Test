package com.potatomeme.chirang_note_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.potatomeme.chirang_note_app.data.dao.NoteDao
import com.potatomeme.chirang_note_app.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}