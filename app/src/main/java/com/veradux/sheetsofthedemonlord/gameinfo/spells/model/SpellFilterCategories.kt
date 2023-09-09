package com.veradux.sheetsofthedemonlord.gameinfo.spells.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell.Property
import com.veradux.sheetsofthedemonlord.ui.composables.Filter
import com.veradux.sheetsofthedemonlord.ui.composables.FilterCategory

class SpellFilterCategories(
    private val traditionFilterCategory: FilterCategory = FilterCategory(
        nameRes = R.string.traditions,
        icon = Icons.Rounded.Star,
        filters = Spell.traditions.map { it.name }
    ),
    private val levelFilterCategory: FilterCategory = FilterCategory(
        nameRes = R.string.level,
        icon = Icons.Rounded.Star,
        filters = (1..5).map { it.toString() }
    ),
    private val propertyFilterCategory: FilterCategory = FilterCategory(
        nameRes = R.string.properties,
        icon = Icons.Rounded.Star,
        filters = listOf(Property.REQUIREMENT, Property.TARGET, Property.AREA, Property.DURATION)
    ),
    private val sourceBookFilterCategory: FilterCategory = FilterCategory(
        nameRes = R.string.source_book,
        icon = Icons.Rounded.Star,
        filters = listOf("Shadow of the Demon Lord", "Occult Philosophy")
    )
) {

    fun filter(spell: Spell): Boolean {
        // In the book, the spells have their traditions written in all capital letters,
        // but in the filters screen the traditions are written with the first letter capitalized only.
        // This is why capitalizeFirstChar() is used, to make them match.
        val hasTradition = traditionFilterCategory.filters.anyMatch(spell.tradition.capitalizeFirstChar())
        val isLevel = levelFilterCategory.filters.anyMatch(spell.level.toString())
        val hasBook = sourceBookFilterCategory.filters.anyMatch(spell.sourceBook)
        val hasProperties = spell.propertiesList().any { propertyFilterCategory.filters.anyMatch(it) }
        return hasTradition && isLevel && hasBook && hasProperties
    }

    fun getFilterCategories(): List<FilterCategory> = listOf(
        traditionFilterCategory,
        levelFilterCategory,
        propertyFilterCategory,
        sourceBookFilterCategory
    )

    /**
     * If all filters are disabled for a certain category,
     * that category will be ignored and all spells will be shown from it.
     */
    private fun List<Filter>.anyMatch(string: String): Boolean =
        this.all { !it.isEnabled.value } || this.any { it.isEnabled.value && string.contains(it.name) }

    private fun String.capitalizeFirstChar() = lowercase().replaceFirstChar { it.uppercase() }

}
