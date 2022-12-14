package com.ironmeddie.notes.di

import android.app.Application
import androidx.room.Room
import com.ironmeddie.notes.feature_note.data.data_source.NoteDatabase
import com.ironmeddie.notes.feature_note.data.repository.NoteRepositoryImpl
import com.ironmeddie.notes.feature_note.domain.repository.NoteRepository
import com.ironmeddie.notes.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {

    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
    ).build()


    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository = NoteRepositoryImpl(db.noteDao)

    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases =
        NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        )
}