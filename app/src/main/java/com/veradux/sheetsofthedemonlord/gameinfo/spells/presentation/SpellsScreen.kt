package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

// Experimental api is for the keyboard controller
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpellsScreen(viewModel: SpellsScreenViewModel = SpellsScreenViewModel()) {
    // filter dialog states
    var isFilterDialogOpen by remember { mutableStateOf(false) }
    val selectedTraditions = remember { mutableStateListOf<String>() }
    val selectedLevels = remember { mutableStateListOf<String>() }
    val selectedProperties = remember { mutableStateListOf<String>() }
    val selectedSourceBooks = remember { mutableStateListOf<String>() }
    val filteredSpells = remember {
        mutableStateListOf(
            *viewModel.getFilteredSpells(
                selectedTraditions, selectedLevels, selectedProperties, selectedSourceBooks
            ).toTypedArray()
        )
    }

    // search bar states
    var searchBarText by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val onFiltersApplied = {
        isFilterDialogOpen = false
        filteredSpells.clear()
        // TODO check if performance of addAll is bad
        filteredSpells.addAll(
            viewModel.getFilteredSpells(
                selectedTraditions,
                selectedLevels,
                selectedProperties,
                selectedSourceBooks,
                searchBarText
            )
        )
    }

    Column {
        // TODO this is the search bar, move it to a composable fun
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, CircleShape),
            value = searchBarText,
            onValueChange = {
                // TODO listen to this change to filter spells
                searchBarText = it.lowercase()
                onFiltersApplied()
            },
            placeholder = { Text(text = stringResource(R.string.spell_search_hint)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }),
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { isFilterDialogOpen = true }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Filter spells")
                }
            }
        )
        if (filteredSpells.isEmpty())
            Text(text = "No spells", modifier = Modifier.align(Alignment.CenterHorizontally))
        else
            LazyColumn {
                items(filteredSpells) { Spell(it) }
            }
    }

    if (isFilterDialogOpen) {
        Dialog(
            onDismissRequest = { onFiltersApplied() }, properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Button(modifier = Modifier.align(Alignment.End), onClick = { onFiltersApplied() }) {
                        Text(text = stringResource(R.string.filter))
                    }

                    FilterCategory(R.string.traditions, selectedTraditions, viewModel.traditionFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.level, selectedLevels, viewModel.levelFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.properties, selectedProperties, viewModel.propertyFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.source_book, selectedSourceBooks, viewModel.sourceBookFilters)
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    // TODO move the text field search bar here
}

@Composable
fun Spell(spell: Spell) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        // spell title
        Row {
            Text(
                text = spell.name,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = " ${spell.tradition} ${spell.type} ${spell.level}",
                modifier = Modifier.align(Alignment.Bottom)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 4.dp))

        // spell properties
        spell.getPropertiesText().let {
            if (it.isNotEmpty()) {
                Text(text = textBoldKeywords(it, Spell.propertyKeywords))
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

        // spell description
        Text(
            text = textBoldKeywords(spell.description, Spell.descriptionKeywords),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(
    @StringRes categoryNameResource: Int,
    selectedFilters: SnapshotStateList<String>,
    filters: List<String>
) {
    Text(
        text = stringResource(categoryNameResource),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp),
    )

    FlowRow {
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                label = { Text(filter) },
                onClick = {
                    if (selectedFilters.contains(filter)) {
                        selectedFilters.remove(filter)
                    } else {
                        selectedFilters.add(filter)
                    }
                },
                selected = selectedFilters.contains(filter),
            )
        }
    }
}

private fun textBoldKeywords(text: String, keywords: List<String>): AnnotatedString = buildAnnotatedString {
    var startIndex = 0
    keywords.filter { text.contains(it) }.forEach { keyword ->
        val indexOfKeyword = text.indexOf(keyword)
        append(text.substring(startIndex, indexOfKeyword))
        startIndex = indexOfKeyword + keyword.length
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(keyword)
        }
    }
    append(text.substring(startIndex, text.length))
}
