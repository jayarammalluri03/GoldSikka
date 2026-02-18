package com.jayaram.goldsikka.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jayaram.goldsikka.model.dao.NoteDao
import com.jayaram.goldsikka.model.database.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideNoteDb(@ApplicationContext applicationContext: Context): NoteDataBase{
        return Room.databaseBuilder(applicationContext,
            NoteDataBase::class.java,"note_db").build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(
        database: NoteDataBase
    ): NoteDao {
        return database.getNoteDao()
    }

}