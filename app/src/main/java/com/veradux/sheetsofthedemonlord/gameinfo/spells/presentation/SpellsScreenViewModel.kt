package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.spells

class SpellsScreenViewModel : ViewModel() {
    val searchText = mutableStateOf("")
    val filteredSpells = spells.toMutableList()

    // The booleans signify if the filter is enabled.
    private val filters = mapOf<FilterCategory, Map<String, Boolean>>(
        FilterCategory.TRADITIONS to mutableMapOf(
            "Arcana" to false,
            "Air" to false,
            "Alteration" to false,
            "Battle" to false,
            "Conjuration" to false
        ),
        FilterCategory.LEVEL to mutableMapOf(
            "0" to false,
            "1" to false,
            "2" to false,
            "3" to false,
            "4" to false,
            "5" to false
        ),
        FilterCategory.PROPERTIES to mutableMapOf(
            "Requirement" to false,
            "Area" to false,
            "Target" to false,
            "Duration" to false
        ),
        FilterCategory.SOURCE_BOOK to mutableMapOf(
            "Shadow of the Demon Lord" to false,
            "Occult Philosophy" to false
        ),
    )

    private fun areAllFiltersOff() =
        !filters.any { filterCategory -> filterCategory.value.any { filter -> filter.value } }

    fun getFilteredSpells(): List<Spell> =
        if (areAllFiltersOff()) filteredSpells
        else filteredSpells.filter { spell ->
            filters.any { category ->
                when (category.key) {
                    // the "it.value" is a boolean which represents if the filter is on
                    FilterCategory.LEVEL -> category.value.any { it.value && it.key == spell.level.toString() }
                    FilterCategory.TRADITIONS -> category.value.any { it.value && it.key == spell.tradition }
                    FilterCategory.PROPERTIES -> category.value.any { it.value && spell.description.contains(it.key) }
                    FilterCategory.SOURCE_BOOK -> category.value.any { it.value && it.key == spell.sourceBook }
                }
            }
        }
}

enum class FilterCategory {
    TRADITIONS, LEVEL, PROPERTIES, SOURCE_BOOK
}
