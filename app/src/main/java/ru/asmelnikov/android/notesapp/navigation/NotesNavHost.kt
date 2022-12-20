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

sealed class NavRoute(val route: String) {
    object Start : NavRoute("start_screen")
    object Main : NavRoute("main_screen")
    object Add : NavRoute("add_screen")
    object Note : NavRoute("note_screen")
}

@Composable
fun NotesNavHost(mViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) { StartScreen(navController = navController, viewModel = mViewModel) }
        composable(NavRoute.Main.route) { MainScreen(navController = navController, viewModel = mViewModel) }
        composable(NavRoute.Add.route) { AddScreen(navController = navController, viewModel = mViewModel) }
        composable(NavRoute.Note.route) { NoteScreen(navController = navController, viewModel = mViewModel) }
    }
}