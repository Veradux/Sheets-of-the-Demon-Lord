package com.veradux.sheetsofthedemonlord.gameinfo.model

data class Spell(
    val name: String,
    val tradition: String,
    val type: Type,
    val level: Int,
    val description: String
) {
    enum class Type {
        UTILITY, ATTACK
    }

    companion object {

        data class Tradition(
            val name: String,
            val attribute: Attribute,
            var description: String = "",
        ) {
            enum class Attribute {
                INTELLECT, WILL
            }
        }

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