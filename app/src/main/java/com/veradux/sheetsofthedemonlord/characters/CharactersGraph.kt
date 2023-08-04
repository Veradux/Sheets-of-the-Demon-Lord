package com.veradux.sheetsofthedemonlord.characters

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterCreation
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterList
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterRoute
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterSheet
import com.veradux.sheetsofthedemonlord.characters.charactercreation.presentation.CharacterCreationScreen
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.characters.characterlist.presentation.CharactersListScreen
import com.veradux.sheetsofthedemonlord.characters.charactersheet.presentation.CharacterSheetScreen

// todo replace this with vm
lateinit var demonLordCharacter: DemonLordCharacter

fun NavGraphBuilder.charactersGraph(navController: NavController) {
    navigation(
        startDestination = CharacterList.name,
        route = CharacterRoute.name
    ) {

        composable(route = CharacterList.name) {
            CharactersListScreen(
                onNewCharacterButtonClicked = {
                    navController.navigate(CharacterCreation.name)
                }, onCharacterSelectedButtonClicked = {
                    demonLordCharacter = it
                    navController.navigate(CharacterSheet.name)
                })
        }

        composable(route = CharacterCreation.name) {
            CharacterCreationScreen()
        }

        composable(route = CharacterSheet.name) {
            CharacterSheetScreen(demonLordCharacter)
        }
    }
}
