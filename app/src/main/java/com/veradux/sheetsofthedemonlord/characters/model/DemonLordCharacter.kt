package com.veradux.sheetsofthedemonlord.characters.model

data class DemonLordCharacter(
    val name: String,
    val level: Int,
    val ancestryName: String,
    val novicePath: NovicePath
)
