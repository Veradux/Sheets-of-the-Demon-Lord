package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen
import com.veradux.sheetsofthedemonlord.characters.charactersNavGraph
import com.veradux.sheetsofthedemonlord.gameinfo.parsePdfSpellsToJson
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parsePdfSpellsToJson(assets.open("text/some spells.txt"))

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
        startDestination = CharactersScreen.ROUTE,
    ) {
        charactersNavGraph(navController)
        // add other graphs for the other screens here
    }
}
