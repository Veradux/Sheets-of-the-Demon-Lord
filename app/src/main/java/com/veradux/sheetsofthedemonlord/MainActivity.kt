package com.veradux.sheetsofthedemonlord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.veradux.sheetsofthedemonlord.characters.data.mock.CharactersApiMock
import com.veradux.sheetsofthedemonlord.characters.presentation.CharacterList
import com.veradux.sheetsofthedemonlord.ui.theme.SheetsOfTheDemonLordTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SheetsOfTheDemonLordTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // TODO figure out how to use view models and APIs
                    val api = CharactersApiMock()
                    CharacterList(characters = api.getCharacters())
                }
            }
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
