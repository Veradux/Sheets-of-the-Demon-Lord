package com.veradux.sheetsofthedemonlord.navigationdrawer

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen
import kotlinx.coroutines.launch

@Composable
fun DemonLordNavigationDrawer(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    selectedScreenState: MutableState<String>,
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
                            selected = button.screenRoute == selectedScreenState.value,
                            onClick = {
                                if (button.screenRoute != null) {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(button.screenRoute)
                                    selectedScreenState.value = button.screenRoute
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
    // TODO replace this idea with a boolean isImplemented
    // TODO the nullable is added temporarily to be used for nav buttons which do not lead to a screen yet.
    //  When all screens are developed, the nullable can be removed.
    val screenRoute: String?,
    val icon: ImageVector
)
