package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.veradux.sheetsofthedemonlord.gameinfo.parsePdfSpells
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!BuildConfig.DEBUG) {
            firebaseAnalytics = Firebase.analytics
        }
        updateSpellsFromFile(false)

        setContent {
            SheetsOfTheDemonLordTheme {
                SheetsOfTheDemonLordApp()
            }
        }
    }

    private fun updateSpellsFromFile(shouldUpdate: Boolean) {
        val spells = parsePdfSpells(
            input = assets.open("text/some spells.txt"),
            sourceBook = "Shadow of the Demon Lord"
        )
        Firebase.database.reference.child("spells").setValue(spells)
    }
}
