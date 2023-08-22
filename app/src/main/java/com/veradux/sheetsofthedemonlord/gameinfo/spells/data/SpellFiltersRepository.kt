package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilters

interface SpellFiltersRepository {
    fun getSpellFilters(): SpellFilters
}

class SpellFiltersRepositoryMock : SpellFiltersRepository {
    override fun getSpellFilters(): SpellFilters = SpellFilters(
        traditionFilters = Spell.traditions.map { it.name }.sorted(),
        levelFilters = listOf("0", "1", "2", "3", "4", "5"),
        propertyFilters = listOf("Requirement", "Area", "Target", "Duration"),
        sourceBookFilters = listOf("Shadow of the Demon Lord", "Occult Philosophy")
    )
}
