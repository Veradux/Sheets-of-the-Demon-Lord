package com.veradux.sheetsofthedemonlord.charactercreation.model

import com.veradux.sheetsofthedemonlord.charactercreation.model.CharacterSize.Medium

data class Ancestry(
    val name: String,
    val attributeScores: AttributeScores,
    val perceptionBonus: Int = 0,
    val defenseOverride: Int = 0,
    val healthBonus: Int = 0,
    val sizeChoices: List<CharacterSize> = listOf(Medium),
    val speed: Int,
    val corruption: Int = 0,
    val languages: List<Language>,
    val expertHealthIncrease: Int,
    val noviceFeatures: List<Feature>,
    val expertFeatures: List<Feature>
)

enum class CharacterSize {
    Small,
    Medium,
    Large
}
