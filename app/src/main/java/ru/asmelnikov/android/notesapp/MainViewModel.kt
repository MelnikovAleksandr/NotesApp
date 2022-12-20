package ru.asmelnikov.android.notesapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.asmelnikov.android.notesapp.database.room.AppRoomDatabase
import ru.asmelnikov.android.notesapp.database.room.repository.RoomRepository
import ru.asmelnikov.android.notesapp.model.Note
import ru.asmelnikov.android.notesapp.utils.REPOSITORY
import ru.asmelnikov.android.notesapp.utils.TYPE_FIREBASE
import ru.asmelnikov.android.notesapp.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application //private?

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        Log.d("checkData", "MainViewModel initDataBase with type: $type")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
        }
    }
}

class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}