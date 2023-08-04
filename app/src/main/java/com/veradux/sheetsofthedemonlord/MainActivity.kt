package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen
import com.veradux.sheetsofthedemonlord.characters.charactersNavGraph
import com.veradux.sheetsofthedemonlord.gameinfo.parsePdfSpells
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spells = parsePdfSpells(assets.open("text/some spells.txt"))
        Log.d("pdf", Gson().toJson(spells))

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
