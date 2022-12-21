package ru.asmelnikov.android.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.asmelnikov.android.notesapp.MainViewModel
import ru.asmelnikov.android.notesapp.screens.AddScreen
import ru.asmelnikov.android.notesapp.screens.MainScreen
import ru.asmelnikov.android.notesapp.screens.NoteScreen
import ru.asmelnikov.android.notesapp.screens.StartScreen
import ru.asmelnikov.android.notesapp.utils.Constants.Keys.ID
import ru.asmelnikov.android.notesapp.utils.Constants.Screens.ADD_SCREEN
import ru.asmelnikov.android.notesapp.utils.Constants.Screens.MAIN_SCREEN
import ru.asmelnikov.android.notesapp.utils.Constants.Screens.NOTE_SCREEN
import ru.asmelnikov.android.notesapp.utils.Constants.Screens.START_SCREEN

sealed class NavRoute(val route: String) {
    object Start : NavRoute(START_SCREEN)
    object Main : NavRoute(MAIN_SCREEN)
    object Add : NavRoute(ADD_SCREEN)
    object Note : NavRoute(NOTE_SCREEN)
}

@Composable
fun NotesNavHost(mViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) {
            StartScreen(
                navController = navController,
                viewModel = mViewModel
            )
        }
        composable(NavRoute.Main.route) {
            MainScreen(
                navController = navController,
                viewModel = mViewModel
            )
        }
        composable(NavRoute.Add.route) {
            AddScreen(
                navController = navController,
                viewModel = mViewModel
            )
        }
        composable(NavRoute.Note.route + "/{${ID}}") { backStackEntry ->
            NoteScreen(
                navController = navController,
                viewModel = mViewModel,
                noteId = backStackEntry.arguments?.getString(ID)

            )
        }
    }
}