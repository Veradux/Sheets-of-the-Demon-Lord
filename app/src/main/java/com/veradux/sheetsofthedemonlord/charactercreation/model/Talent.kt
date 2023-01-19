package com.veradux.sheetsofthedemonlord.charactercreation.model

sealed interface Talent {

    enum class Basic : Talent {
        AddALanguage,
        AddAProfession,
        LearnASpell,
        IncreaseAttributeScoreBy1,
        IncreaseAttributeScoreBy2,
    }

    open class Detailed(
        val name: String,
        val description: String,
        val choiceOptions: List<Talent>? = null
    ) : Talent
}
