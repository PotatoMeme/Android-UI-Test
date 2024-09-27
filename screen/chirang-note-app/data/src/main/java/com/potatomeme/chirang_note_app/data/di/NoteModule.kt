package com.potatomeme.chirang_note_app.data.di

import android.app.Application
import androidx.room.Room
import com.potatomeme.chirang_note_app.data.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase = Room.databaseBuilder(
        application, NoteDatabase::class.java, "note_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()

}