package com.veradux.sheetsofthedemonlord.gameinfo.spells.model

data class Spell(
    val name: String,
    val tradition: String,
    val type: Type,
    val level: Int,
    val description: String,
    val sourceBook: String,
    val requirement: String? = null,
    val area: String? = null,
    val target: String? = null,
    val duration: String? = null
) {

    /**
     * The empty constructor is necessary for the firebase deserialization.
     */
    constructor() : this(
        name = "",
        tradition = "",
        type = Type.ATTACK,
        level = 0,
        description = "",
        sourceBook = ""
    )

    enum class Type {
        UTILITY, ATTACK
    }

    object Property {
        const val REQUIREMENT = "Requirement"
        const val TARGET = "Target"
        const val AREA = "Area"
        const val DURATION = "Duration"
    }

    data class Tradition(
        val name: String,
        val attribute: Attribute,
        var description: String = "",
    ) {
        enum class Attribute {
            INTELLECT, WILL
        }
    }

    fun propertiesList(): List<String> =
        listOfNotNull(requirement, area, target, duration)

    fun propertiesText(): String =
        listOfNotNull(requirement, area, target, duration).joinToString(separator = "\n")

    fun containsText(text: String): Boolean =
        listOfNotNull(name, tradition, requirement, area, target, duration, description).any {
            it.lowercase().contains(text.lowercase())
        }

    companion object {
        val descriptionKeywords = listOf("Triggered", "Sacrifice", "Permanence", "Attack Roll 20+")
        val propertyKeywords = listOf(Property.REQUIREMENT, Property.TARGET, Property.AREA, Property.DURATION)

        // TODO this map is temporary and will be used to convert to json later and put in a data base,
        //  after which the hardcoded list will be removed, and it will be read from the DB instead.
        val traditions = listOf(
            Tradition("Arcana", Tradition.Attribute.INTELLECT),
            Tradition("Air", Tradition.Attribute.WILL),
            Tradition("Battle", Tradition.Attribute.INTELLECT),
            Tradition("Alteration", Tradition.Attribute.WILL),
            Tradition("Conjuration", Tradition.Attribute.INTELLECT),
            Tradition("Celestial", Tradition.Attribute.WILL),
            Tradition("Curse", Tradition.Attribute.INTELLECT),
            Tradition("Chaos", Tradition.Attribute.WILL),
            Tradition("Divination", Tradition.Attribute.INTELLECT),
            Tradition("Destruction", Tradition.Attribute.WILL),
            Tradition("Enchantment", Tradition.Attribute.INTELLECT),
            Tradition("Earth", Tradition.Attribute.WILL),
            Tradition("Forbidden", Tradition.Attribute.INTELLECT),
            Tradition("Fire", Tradition.Attribute.WILL),
            Tradition("Illusion", Tradition.Attribute.INTELLECT),
            Tradition("Life", Tradition.Attribute.WILL),
            Tradition("Necromancy", Tradition.Attribute.INTELLECT),
            Tradition("Nature", Tradition.Attribute.WILL),
            Tradition("Protection", Tradition.Attribute.INTELLECT),
            Tradition("Primal", Tradition.Attribute.WILL),
            Tradition("Rune", Tradition.Attribute.INTELLECT),
            Tradition("Song", Tradition.Attribute.WILL),
            Tradition("Shadow", Tradition.Attribute.INTELLECT),
            Tradition("Storm", Tradition.Attribute.WILL),
            Tradition("Technomancy", Tradition.Attribute.INTELLECT),
            Tradition("Theurgy", Tradition.Attribute.WILL),
            Tradition("Teleportation", Tradition.Attribute.INTELLECT),
            Tradition("Transformation", Tradition.Attribute.WILL),
            Tradition("Time", Tradition.Attribute.INTELLECT),
            Tradition("Water", Tradition.Attribute.WILL),
        )
    }
}
