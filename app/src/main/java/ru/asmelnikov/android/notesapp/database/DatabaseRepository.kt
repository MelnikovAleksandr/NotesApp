package ru.asmelnikov.android.notesapp.database

import androidx.lifecycle.LiveData
import ru.asmelnikov.android.notesapp.model.Note

interface DatabaseRepository {

    val readAll: LiveData<List<Note>>

    suspend fun create(note: Note, onSuccess: () -> Unit)
    suspend fun update(note: Note, onSuccess: () -> Unit)
    suspend fun delete(note: Note, onSuccess: () -> Unit)

    fun singOut() {}
    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {}
}