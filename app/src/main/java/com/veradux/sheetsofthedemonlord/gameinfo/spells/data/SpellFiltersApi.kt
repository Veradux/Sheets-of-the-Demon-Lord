package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories

interface SpellFiltersApi {
    fun getSpellFilters(): SpellFilterCategories
}

class SpellFiltersApiMock : SpellFiltersApi {
    override fun getSpellFilters(): SpellFilterCategories = SpellFilterCategories(
        traditionFilters = Spell.traditions.map { it.name }.sorted().map { SpellFilterCategories.Filter(it) },
        levelFilters = listOf("0", "1", "2", "3", "4", "5").map { SpellFilterCategories.Filter(it) },
        propertyFilters = listOf("Requirement", "Area", "Target", "Duration").map { SpellFilterCategories.Filter(it) },
        sourceBookFilters = listOf("Shadow of the Demon Lord", "Occult Philosophy").map { SpellFilterCategories.Filter(it) }
    )
}
