package com.veradux.sheetsofthedemonlord.characters.characterlist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

@Composable
fun CharactersListScreen(
    onNewCharacterButtonClicked: () -> Unit,
    onCharacterSelectedButtonClicked: (DemonLordCharacter) -> Unit,
    viewModel: CharactersListViewModel = CharactersListViewModel()
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNewCharacterButtonClicked() },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.new_character),
                )
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues), color = MaterialTheme.colorScheme.background) {
            val characters = viewModel.getCharacters()
            if (characters.isNotEmpty()) {
                CharacterList(onCharacterSelectedButtonClicked, characters)
            } else {
                Text(
                    text = stringResource(R.string.create_first_character),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp,
                    modifier = Modifier
                        .padding(40.dp)
                )
            }
        }
    }
}

@Composable
fun CharacterCard(onCharacterSelectedButtonClicked: (DemonLordCharacter) -> Unit, character: DemonLordCharacter) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clickable { onCharacterSelectedButtonClicked(character) }) {
        Column {
            Box {
                Image(
                    painter = painterResource(character.novicePath.mapToDrawable()),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier.height(112.dp)
                )
                // This box is for a "Scrim" effect, which darkens the image behind the text for better readability.
                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )
                // This text is always white, regardless of theme, 
                //  because it is on top of an image and needs guaranteed high contrast.
                Text(
                    text = character.name,
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart)
                )
            }
            Text(
                text = with(character) {
                    stringResource(R.string.character_card_description, level, ancestryName, novicePath.name)
                },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun CharacterList(
    onCharacterSelectedButtonClicked: (DemonLordCharacter) -> Unit,
    characters: List<DemonLordCharacter>
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(characters) { character ->
            CharacterCard(onCharacterSelectedButtonClicked, character)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    SheetsOfTheDemonLordTheme {
        CharactersListScreen({}, {})
    }
}
