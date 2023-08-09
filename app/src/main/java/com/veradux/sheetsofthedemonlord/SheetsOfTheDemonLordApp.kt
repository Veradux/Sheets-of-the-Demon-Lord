package com.veradux.sheetsofthedemonlord

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen
import com.veradux.sheetsofthedemonlord.characters.charactersNavGraph
import com.veradux.sheetsofthedemonlord.navigationdrawer.DemonLordNavigationDrawer

@Composable
fun SheetsOfTheDemonLordApp() {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues), color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val selectedScreenState = remember { mutableStateOf(CharactersScreen.LIST) }
            DemonLordNavigationDrawer(navController, snackBarHostState, selectedScreenState) {
                NavHost(navController = navController, startDestination = CharactersScreen.ROUTE) {
                    charactersNavGraph(navController) { newValue ->
                        selectedScreenState.value = newValue
                    }
                    // add other graphs for the other screens here
                }
            }
        }
    }
}
