package com.veradux.sheetsofthedemonlord

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen
import com.veradux.sheetsofthedemonlord.characters.charactersNavGraph
import kotlinx.coroutines.launch

@Composable
fun SheetsOfTheDemonLordApp() {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues), color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val drawerViewModel = NavDrawerViewModel()
            DemonLordNavigationDrawer(navController, snackBarHostState, drawerViewModel) {
                NavHost(navController = navController, startDestination = CharactersScreen.ROUTE) {

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
    snackBarHostState: SnackbarHostState,
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
                Text(
                    text = stringResource(R.string.app_name).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(32.dp)
                )
                getDemonLordNavButtons().forEach { button ->
                    button?.let {
                        NavigationDrawerItem(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            label = { Text(text = stringResource(button.label)) },
                            icon = { Icon(button.icon, stringResource(button.label)) },
                            selected = viewModel.isButtonSelected(button.screenRoute ?: ""),
                            onClick = {
                                if (button.screenRoute != null) {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(button.screenRoute)
                                    viewModel.updateSelectedButtonState(button.screenRoute)
                                } else {
                                    scope.launch {
                                        snackBarHostState.showSnackbar("This feature will be implemented soon!")
                                    }
                                }
                            }
                        )
                    }
                    if (button == null) Divider(modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp))
                }
            }
        }
    )
}

/**
 * The order of the returned list determines the order of the UI buttons in the drawer.
 * The list has nullable objects and the nulls are used to signify positions for dividers in the drawer.
 */
private fun getDemonLordNavButtons(): List<NavButton?> = listOf(
    NavButton(R.string.characters, CharactersScreen.LIST, Icons.Default.List),
    NavButton(R.string.new_character, CharactersScreen.CREATION, Icons.Default.Add),
    null,
    NavButton(R.string.spells, null, Icons.Default.Info),
    NavButton(R.string.ancestries, null, Icons.Default.Face),
    NavButton(R.string.combat_actions, null, Icons.Default.KeyboardArrowDown)
)

private data class NavButton(
    @StringRes val label: Int,
    // TODO the nullable is added temporarily to be used for nav buttons which do not lead to a screen yet.
    //  When all screens are developed, the nullable can be removed.
    val screenRoute: String?,
    val icon: ImageVector
)
