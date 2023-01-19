package com.veradux.sheetsofthedemonlord.charactercreation.model

sealed interface Feature {

    enum class Basic : Feature {
        AddALanguage,
        AddAProfession,
        LearnASpell,
        IncreaseAttributeScoreBy1,
        IncreaseAttributeScoreBy2,
    }

    open class Detailed(
        val name: String,
        val description: String,
        val choiceOptions: List<Feature>? = null
    ) : Feature
}
