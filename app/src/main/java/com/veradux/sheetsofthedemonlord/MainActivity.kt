package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.veradux.sheetsofthedemonlord.characters.data.mock.CharactersApiMock
import com.veradux.sheetsofthedemonlord.characters.presentation.CharacterList
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SheetsOfTheDemonLordTheme {
                Scaffold(
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* OnClick Method */ },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Add Character Sheet",
                            )
                        }
                    }
                ) {
                    CharactersList(it)
                }
            }
        }
    }
}

@Composable
fun CharactersList(paddingValues: PaddingValues) {
    Surface(modifier = Modifier.padding(paddingValues), color = MaterialTheme.colorScheme.background) {
        // TODO figure out how to use view models and APIs
        val api = CharactersApiMock()
        CharacterList(characters = api.getCharacters())
    }
}
