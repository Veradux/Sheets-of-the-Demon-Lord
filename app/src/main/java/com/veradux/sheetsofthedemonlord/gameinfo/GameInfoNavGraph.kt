package com.veradux.sheetsofthedemonlord.gameinfo

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation.SpellsScreen

// TODO think about the necessity of this nav graph.
//  It might not be needed because the game info screens do not navigate between each other so far.
fun NavGraphBuilder.gameInfoNavGraph(setNavDrawerSelectionTo: (String) -> Unit ) {
    navigation(startDestination = GameInfoScreens.SPELLS, route = GameInfoScreens.ROUTE) {

        composable(route = GameInfoScreens.SPELLS) {
            setNavDrawerSelectionTo(GameInfoScreens.SPELLS)
            SpellsScreen()
        }
        // TODO add other screens here
    }
}
