package com.veradux.sheetsofthedemonlord.charactercreation.data.mockdata

import com.veradux.sheetsofthedemonlord.charactercreation.model.Ancestry
import com.veradux.sheetsofthedemonlord.charactercreation.model.AttributeScores
import com.veradux.sheetsofthedemonlord.charactercreation.model.CharacterSize
import com.veradux.sheetsofthedemonlord.charactercreation.model.Feature
import com.veradux.sheetsofthedemonlord.charactercreation.model.Language

// region generic features
object Shadowsight : Feature.Detailed(
    name = "Shadowsight",
    description = "You see in areas obscured by shadows as if those areas were lit."
)
// endregion

val human = Ancestry(
    name = "Human",
    attributeScores = AttributeScores(strength = 10, agility = 10, intellect = 10, will = 10),
    sizeChoices = listOf(CharacterSize.Small, CharacterSize.Medium),
    speed = 10,
    languages = listOf(Language.CommonTongue),
    expertHealthIncrease = 5,
    noviceFeatures = listOf(
        Feature.Basic.IncreaseAttributeScoreBy1,
        Feature.Detailed(
            name = "Language and Professions",
            description = "You speak the Common Tongue, and you can either speak one additional language or add a random profession.",
            choiceOptions = listOf(Feature.Basic.AddALanguage, Feature.Basic.AddAProfession)
        )
    ),
    expertFeatures = listOf(
        Feature.Detailed(
            name = "Characteristics",
            description = "You either learn one spell or gain Determined.",
            choiceOptions = listOf(
                Feature.Basic.LearnASpell,
                Feature.Detailed(
                    name = "Determined",
                    description = "When you roll a 1 on the die from a boon, you can reroll the die and choose to use the new number."
                )
            )
        )
    )
)

val goblin = Ancestry(
    name = "Goblin",
    attributeScores = AttributeScores(strength = 8, agility = 12, intellect = 10, will = 9),
    perceptionBonus = 1,
    sizeChoices = listOf(CharacterSize.Small),
    speed = 10,
    expertHealthIncrease = 4,
    languages = listOf(Language.CommonTongue, Language.Elvish),
    noviceFeatures = listOf(
        Feature.Detailed(
            name = "Immune",
            description = "damage from disease; charmed, diseased"
        ),
        Feature.Detailed(
            name = "Iron Vulnerability",
            description = "You are impaired while you are in contact with iron."
        ),
        Shadowsight,
        Feature.Detailed(
            name = "Sneaky",
            description = "When you roll to become hidden or move silently, you make the Agility challenge roll with 1 boon."
        )
    ),
    expertFeatures = listOf(
        Feature.Detailed(
            name = "Characteristics",
            description = "You either learn one spell or gain Cunning Escape.",
            choiceOptions = listOf(
                Feature.Basic.LearnASpell,
                Feature.Detailed(
                    name = "Spring Away",
                    description = "When a creature you can see gets a failure on an attack roll against your Defense or Agility, you can use a triggered action to retreat."
                )
            )
        )
    )
)
