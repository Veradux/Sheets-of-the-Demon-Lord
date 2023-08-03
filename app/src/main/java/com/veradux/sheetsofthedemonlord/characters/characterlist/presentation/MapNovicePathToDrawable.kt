package com.veradux.sheetsofthedemonlord.characters.characterlist.presentation

import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.NovicePath

// This extension is in its own file because there are many other paths to be added.
//  The list is expected to be very long.
fun NovicePath.mapToDrawable() = when (this) {
    NovicePath.Cleric -> R.drawable.bg_cleric
    NovicePath.Warrior -> R.drawable.bg_warrior
    NovicePath.Rogue -> R.drawable.bg_rogue
    NovicePath.Magician -> R.drawable.bg_magician
}
