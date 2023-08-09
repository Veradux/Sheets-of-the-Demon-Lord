package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.gson.Gson
import com.veradux.sheetsofthedemonlord.gameinfo.parsePdfSpells
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spells = parsePdfSpells(assets.open("text/some spells.txt"))
        Log.d("pdf", Gson().toJson(spells))

        setContent {
            SheetsOfTheDemonLordTheme {
                SheetsOfTheDemonLordApp()
            }
        }
    }
}
