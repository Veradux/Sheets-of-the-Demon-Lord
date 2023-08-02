package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veradux.sheetsofthedemonlord.characters.presentation.CharactersListScreen
import com.veradux.sheetsofthedemonlord.ui.navigation.CharacterSheetScreens
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

@Composable
fun MainMenuNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = CharacterSheetScreens.CharacterList.name,
    ) {
        composable(route = CharacterSheetScreens.CharacterList.name) {
            CharactersListScreen(navController)
        }
        composable(route = CharacterSheetScreens.CharacterCreation.name) {
            // TODO add creation screen
        }
        composable(route = CharacterSheetScreens.CharacterSheet.name) {
            // TODO add sheet screen
        }
    }
}
