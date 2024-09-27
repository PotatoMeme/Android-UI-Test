package com.potatomeme.chirang_note_app.data.di

import com.potatomeme.chirang_note_app.data.repository.NoteRepositoryImpl
import com.potatomeme.chirang_note_app.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteDomainModule {
    @Binds
    abstract fun bindNoteRepository(repository: NoteRepositoryImpl): NoteRepository
}