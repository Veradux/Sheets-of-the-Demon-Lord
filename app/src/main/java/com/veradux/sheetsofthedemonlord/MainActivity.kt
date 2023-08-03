package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.veradux.sheetsofthedemonlord.characters.charactercreation.presentation.CharacterCreationScreen
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.characters.characterlist.presentation.CharactersListScreen
import com.veradux.sheetsofthedemonlord.characters.charactersheet.presentation.CharacterSheetScreen
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterCreation
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterList
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterRoute
import com.veradux.sheetsofthedemonlord.characters.CharactersScreens.CharacterSheet
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            SheetsOfTheDemonLordTheme {
                MainMenuNavHost(navController)
            }
        }
    }
}

// todo replace this with vm
lateinit var demonLordCharacter: DemonLordCharacter

@Composable
fun MainMenuNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = CharacterRoute.name,
    ) {
        characterGraph(navController)
    }
}

fun NavGraphBuilder.characterGraph(navController: NavController) {
    navigation(startDestination = CharacterList.name, route = CharacterRoute.name) {

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
