package com.veradux.sheetsofthedemonlord.gameinfo.spells.model

data class SpellFilterCategories(
    val traditionFilters: List<Filter>,
    val levelFilters: List<Filter>,
    val propertyFilters: List<Filter>,
    val sourceBookFilters: List<Filter>
) {

    data class Filter(
        val name: String,
        val isEnabled: Boolean = false
    )

    fun filter(spell: Spell): Boolean {
        val hasTradition = traditionFilters.anyMatch(spell.tradition.capitalize())
        val isLevel = levelFilters.anyMatch(spell.level.toString())
        val hasBook = sourceBookFilters.anyMatch(spell.sourceBook)
        val hasProperties = spell.getPropertiesList().any { propertyFilters.anyMatch(it) }
        return hasTradition && isLevel && hasBook && hasProperties
    }

    /**
     * If all filters are disabled for a certain category,
     * that category will be ignored and all spells will be shown from it.
     */
    private fun List<Filter>.anyMatch(string: String): Boolean =
        this.all { !it.isEnabled } || this.filter { it.isEnabled }.map { it.name }.any { string.contains(it) }

    private fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }
}
