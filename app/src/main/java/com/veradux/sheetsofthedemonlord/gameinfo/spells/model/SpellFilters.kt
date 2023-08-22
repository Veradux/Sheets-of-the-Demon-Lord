package com.veradux.sheetsofthedemonlord.gameinfo.spells.model

data class SpellFilters(
    val traditionFilters: List<String>,
    val levelFilters: List<String>,
    val propertyFilters: List<String>,
    val sourceBookFilters: List<String>
) {
    /**
     * If all filters are off for a certain category, ignore that category and show all spells from it.
     */
    fun filter(spell: Spell) =
        (traditionFilters.isEmpty() || traditionFilters.contains(spell.tradition.capitalize())) &&
                (levelFilters.isEmpty() || levelFilters.contains(spell.level.toString())) &&
                (sourceBookFilters.isEmpty() || sourceBookFilters.contains(spell.sourceBook)) &&
                (propertyFilters.isEmpty() || propertyFilters.any { spell.getPropertiesText().contains(it) })

    private fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }
}
