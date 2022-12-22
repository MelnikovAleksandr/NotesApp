package ru.asmelnikov.android.notesapp.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.asmelnikov.android.notesapp.database.DatabaseRepository
import ru.asmelnikov.android.notesapp.model.Note
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.SUBTITLE
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.TITLE
import ru.asmelnikov.android.notesapp.utils.FIREBASE_ID
import ru.asmelnikov.android.notesapp.utils.LOGIN
import ru.asmelnikov.android.notesapp.utils.PASSWORD

class AppFirebaseRepository : DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference
        .child(mAuth.currentUser?.uid.toString())

    override val readAll: LiveData<List<Note>> = AllNotesLiveData()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subTitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "failed to add new note") }
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subTitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Fail to update") }
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        database.child(note.firebaseId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Fail to delete") }
    }

    override fun singOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}