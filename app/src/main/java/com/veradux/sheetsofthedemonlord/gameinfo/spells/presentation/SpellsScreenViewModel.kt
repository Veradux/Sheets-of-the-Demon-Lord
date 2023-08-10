package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.spells

class SpellsScreenViewModel : ViewModel() {

    private val filteredSpells = spells.toMutableList()

    var filters = Filters()

    class Filters(
        val traditions: List<String> = listOf(
            "Arcana",
            "Air",
            "Alteration",
            "Battle",
            "Conjuration",
            // TODO add the rest of the traditions
        ),
        val level: List<String> = listOf("0", "1", "2", "3", "4", "5"),
        val properties: List<String> = listOf("Requirement", "Area", "Target", "Duration"),
        val sourceBook: List<String> = listOf("Shadow of the Demon Lord", "Occult Philosophy")
    ) {
        fun getAllFilters(): List<List<String>> = listOf(traditions, level, properties, sourceBook)
    }
}
