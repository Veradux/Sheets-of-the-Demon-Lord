package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.veradux.sheetsofthedemonlord.gameinfo.parsePdfSpells
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

// TODO fix this and put it in a repository to be accessed by the view model
lateinit var spells: List<Spell>

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spells = parsePdfSpells(
            input = assets.open("text/some spells.txt"),
            sourceBook = "Shadow of the Demon Lord"
        )
        if (!BuildConfig.DEBUG) {
            firebaseAnalytics = Firebase.analytics
        }

        setContent {
            SheetsOfTheDemonLordTheme {
                SheetsOfTheDemonLordApp()
            }
        }
    }
}
