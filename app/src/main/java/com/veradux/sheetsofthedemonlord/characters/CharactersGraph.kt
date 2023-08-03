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

fun NavGraphBuilder.charactersGraph(navController: NavController) {
    navigation(startDestination = CharactersScreens.CharacterList.name, route = CharactersScreens.CharacterRoute.name) {

        composable(route = CharactersScreens.CharacterList.name) {
            CharactersListScreen(
                onNewCharacterButtonClicked = {
                    navController.navigate(CharactersScreens.CharacterCreation.name)
                }, onCharacterSelectedButtonClicked = {
                    demonLordCharacter = it
                    navController.navigate(CharactersScreens.CharacterSheet.name)
                })
        }

        composable(route = CharactersScreens.CharacterCreation.name) {
            CharacterCreationScreen()
        }

        composable(route = CharactersScreens.CharacterSheet.name) {
            CharacterSheetScreen(demonLordCharacter)
        }
    }
}
