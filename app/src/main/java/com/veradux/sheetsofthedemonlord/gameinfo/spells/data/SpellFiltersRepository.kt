package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilters

interface SpellFiltersRepository {
    fun getSpellFilters(): SpellFilters
}

class SpellFiltersRepositoryMock : SpellFiltersRepository {
    override fun getSpellFilters(): SpellFilters = SpellFilters(
        traditionFilters = Spell.traditions.map { it.name }.sorted().map { SpellFilters.Toggle(it) },
        levelFilters = listOf("0", "1", "2", "3", "4", "5").map { SpellFilters.Toggle(it) },
        propertyFilters = listOf("Requirement", "Area", "Target", "Duration").map { SpellFilters.Toggle(it) },
        sourceBookFilters = listOf("Shadow of the Demon Lord", "Occult Philosophy").map { SpellFilters.Toggle(it) }
    )
}
