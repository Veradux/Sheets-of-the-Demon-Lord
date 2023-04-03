package com.veradux.sheetsofthedemonlord.characters.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.characters.model.DemonLordCharacter

@Composable
fun CharacterCard(character: DemonLordCharacter) {

    Card(shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(8.dp)) {

        ListItem(
            headlineContent = { Text(text = character.name) },
            // TODO localization
            supportingContent = { Text(text = with(character) { "Level $level $ancestryName\n$novicePath" }) },
            leadingContent = {
                Image(
                    // TODO add image impl
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
            })
    }
}

@Composable
fun CharacterList(characters: List<DemonLordCharacter>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(characters) { character ->
            CharacterCard(character = character)
        }
    }
}
