package com.veradux.sheetsofthedemonlord.characters.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veradux.sheetsofthedemonlord.characters.data.mock.CharactersApiMock
import com.veradux.sheetsofthedemonlord.characters.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

@Composable
fun CharacterCard(character: DemonLordCharacter) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clickable { /* move to sheet screen */ }) {
        Column {
            Box {
                Image(
                    painter = painterResource(character.novicePath.mapToDrawable()),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier.height(112.dp)
                )
                // This box is a "Scrim", which darkens the image behind the text for better visibility.
                Box(
                    Modifier
                        .matchParentSize()
                        .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Color.Black)))
                )
                // This text is always white, regardless of theme, because it is on top of an image.
                Text(
                    text = character.name,
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.BottomStart)

                )
            }
            Text(
                text = with(character) { "Level $level $ancestryName $novicePath" },
                modifier = Modifier.padding(8.dp)
            )
        }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SheetsOfTheDemonLordTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            // TODO figure out how to use view models and APIs
            val api = CharactersApiMock()
            CharacterList(characters = api.getCharacters())
        }
    }
}

