package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.spells

class SpellsScreenViewModel : ViewModel() {

    val traditionFilters: List<String> = Spell.traditions.map { it.name }.sorted()
    val levelFilters: List<String> = listOf("0", "1", "2", "3", "4", "5")
    val propertyFilters: List<String> = listOf("Requirement", "Area", "Target", "Duration")
    val sourceBookFilters: List<String> = listOf("Shadow of the Demon Lord", "Occult Philosophy")

    fun getFilteredSpells(
        selectedTraditions: List<String>,
        selectedLevels: List<String>,
        selectedProperties: List<String>,
        selectedSourceBook: List<String>
    ): List<Spell> =
        // if all filters are off for a certain category, ignore them and show all spells
        spells.filter { spell ->
            (selectedTraditions.isEmpty() or selectedTraditions.contains(spell.tradition.capitalize())) and
                    (selectedLevels.isEmpty() or selectedLevels.contains(spell.level.toString())) and
                    (selectedSourceBook.isEmpty() or selectedSourceBook.contains(spell.sourceBook)) and
                    (selectedProperties.isEmpty() or selectedProperties.any { spell.description.contains(it) })
        }

    private fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }
}
