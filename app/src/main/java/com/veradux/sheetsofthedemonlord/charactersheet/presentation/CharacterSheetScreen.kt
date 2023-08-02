package com.veradux.sheetsofthedemonlord.charactersheet.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veradux.sheetsofthedemonlord.characters.model.DemonLordCharacter

@Composable
fun CharacterSheetScreen(character: DemonLordCharacter) {
    Text(
        text = character.name,
        fontSize = 64.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(8.dp)
    )
}
