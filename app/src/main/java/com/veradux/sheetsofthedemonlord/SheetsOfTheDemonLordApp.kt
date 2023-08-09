package com.veradux.sheetsofthedemonlord

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen
import com.veradux.sheetsofthedemonlord.characters.charactersNavGraph
import kotlinx.coroutines.launch

@Composable
fun SheetsOfTheDemonLordApp() {
    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues), color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val drawerViewModel = NavDrawerViewModel()
            DemonLordNavigationDrawer(navController, drawerViewModel) {
                NavHost(
                    navController = navController,
                    startDestination = CharactersScreen.ROUTE,
                ) {
                    charactersNavGraph(navController, drawerViewModel::updateSelectedButtonState)
                    // add other graphs for the other screens here
                }
            }
        }
    }
}

@Composable
fun DemonLordNavigationDrawer(
    navController: NavHostController,
    viewModel: NavDrawerViewModel,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        content = content,
        drawerContent = {
            ModalDrawerSheet {
                getDemonLordNavButtons().forEach { button ->
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(button.label)) },
                        icon = { Icon(button.icon, stringResource(button.label)) },
                        selected = viewModel.isButtonSelected(button.screenRoute),
                        onClick = {
                            viewModel.updateSelectedButtonState(button.screenRoute)
                            scope.launch { drawerState.close() }
                            navController.navigate(button.screenRoute)
                        }
                    )
                }
            }
        }
    )
}

private fun getDemonLordNavButtons(): List<NavButton> = listOf(
    NavButton(R.string.new_character, CharactersScreen.CREATION, Icons.Default.Add),
    NavButton(R.string.characters, CharactersScreen.LIST, Icons.Default.List),
    NavButton(R.string.spells, CharactersScreen.CREATION, Icons.Default.Info),
    NavButton(R.string.ancestries, CharactersScreen.CREATION, Icons.Default.Face),
    NavButton(R.string.combat_actions, CharactersScreen.CREATION, Icons.Default.ArrowDropDown)
)

private data class NavButton(
    @StringRes val label: Int,
    val screenRoute: String,
    val icon: ImageVector
)
