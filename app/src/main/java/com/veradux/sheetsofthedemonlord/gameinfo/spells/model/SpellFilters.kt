package com.veradux.sheetsofthedemonlord.gameinfo.spells.model

data class SpellFilters(
    val traditionFilters: List<Toggle>,
    val levelFilters: List<Toggle>,
    val propertyFilters: List<Toggle>,
    val sourceBookFilters: List<Toggle>
) {

    data class Toggle(
        val name: String,
        val isSelected: Boolean = false
    )

    fun filter(spell: Spell): Boolean {
        val hasTradition = traditionFilters.anyMatch(spell.tradition.capitalize())
        val isLevel = levelFilters.anyMatch(spell.level.toString())
        val hasBook = sourceBookFilters.anyMatch(spell.sourceBook)
        val hasProperties = spell.getPropertiesList().any { propertyFilters.anyMatch(it) }
        return hasTradition && isLevel && hasBook && hasProperties
    }

    /**
     * If all filters are off for a certain category,
     * that category will be ignored and all spells will be shown from it.
     */
    private fun List<Toggle>.anyMatch(string: String): Boolean =
        this.all { !it.isSelected } || this.filter { it.isSelected }.map { it.name }.any { string.contains(it) }

    private fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }
}
