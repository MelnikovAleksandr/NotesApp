package ru.asmelnikov.android.notesapp.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.asmelnikov.android.notesapp.MainViewModel
import ru.asmelnikov.android.notesapp.MainViewModelFactory
import ru.asmelnikov.android.notesapp.model.Note
import ru.asmelnikov.android.notesapp.navigation.NavRoute
import ru.asmelnikov.android.notesapp.ui.theme.NotesAppTheme
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.ADD_NEW_NOTE
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.ADD_NOTE
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.NOTE_SUBTITLE
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.NOTE_TITLE


@Composable
fun AddScreen(navController: NavHostController, viewModel: MainViewModel) {
    var title by remember {
        mutableStateOf("")
    }
    var subTitle by remember {
        mutableStateOf("")
    }

    var isButtonEnabled by remember {
        mutableStateOf(false)
    }


    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = ADD_NEW_NOTE,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    isButtonEnabled = title.isNotEmpty() && subTitle.isNotEmpty()
                },
                label = { Text(text = NOTE_TITLE) },
                isError = title.isEmpty()
            )
            OutlinedTextField(
                value = subTitle,
                onValueChange = {
                    subTitle = it
                    isButtonEnabled = title.isNotEmpty() && subTitle.isNotEmpty()
                },
                label = { Text(text = NOTE_SUBTITLE) },
                isError = subTitle.isEmpty()
            )
            Button(
                modifier = Modifier.padding(16.dp),
                enabled = isButtonEnabled,
                onClick = {
                    viewModel.addNote(note = Note(title = title, subTitle = subTitle)) {
                        navController.navigate(NavRoute.Main.route)
                    }
                }) {
                Text(text = ADD_NOTE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevAddScreen() {
    NotesAppTheme {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(
                factory = MainViewModelFactory(
                    context.applicationContext
                            as Application
                )
            )
        AddScreen(navController = rememberNavController(), viewModel = mViewModel)
    }
}
