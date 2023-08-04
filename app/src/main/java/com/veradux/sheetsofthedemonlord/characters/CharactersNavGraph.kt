package com.veradux.sheetsofthedemonlord.characters

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.veradux.sheetsofthedemonlord.characters.charactercreation.presentation.CharacterCreationScreen
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.characters.characterlist.presentation.CharactersListScreen
import com.veradux.sheetsofthedemonlord.characters.charactersheet.presentation.CharacterSheetScreen

// todo replace this with vm
lateinit var demonLordCharacter: DemonLordCharacter

fun NavGraphBuilder.charactersNavGraph(navController: NavController) {
    navigation(startDestination = CharactersScreen.LIST, route = CharactersScreen.ROUTE) {

        composable(route = CharactersScreen.LIST) {
            CharactersListScreen(
                onNewCharacterButtonClicked = {
                    navController.navigate(CharactersScreen.CREATION)
                }, onCharacterSelectedButtonClicked = {
                    demonLordCharacter = it
                    navController.navigate(CharactersScreen.SHEET)
                })
        }

        composable(route = CharactersScreen.CREATION) {
            CharacterCreationScreen()
        }

        composable(route = CharactersScreen.SHEET) {
            CharacterSheetScreen(demonLordCharacter)
        }
    }
}
