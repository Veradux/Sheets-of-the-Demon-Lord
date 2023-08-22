package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.veradux.sheetsofthedemonlord.R

@Composable
fun SpellsScreen(viewModel: SpellsScreenViewModel = SpellsScreenViewModel()) {
    // filter dialog states
    val isFilterDialogOpen by viewModel.isFilterDialogOpen.collectAsState(initial = false)
    val selectedTraditions = remember { mutableStateListOf<String>() }
    val selectedLevels = remember { mutableStateListOf<String>() }
    val selectedProperties = remember { mutableStateListOf<String>() }
    val selectedSourceBooks = remember { mutableStateListOf<String>() }

    val filteredSpells by viewModel.filteredSpells.collectAsState()
    val spellFilters by viewModel.spellFilters.collectAsState()

    Column {
        SpellsSearchBar(viewModel)
        if (filteredSpells.isEmpty())
            Text(text = "No spells", modifier = Modifier.align(Alignment.CenterHorizontally))
        else
            LazyColumn {
                items(filteredSpells) { Spell(it) }
            }
    }

    if (isFilterDialogOpen) {
        Dialog(
            onDismissRequest = { viewModel.setFilterDialogVisibilityStateTo(false) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { viewModel.setFilterDialogVisibilityStateTo(false) }) {
                        Text(text = stringResource(R.string.filter))
                    }

                    FilterCategory(R.string.traditions, selectedTraditions, spellFilters.traditionFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.level, selectedLevels, spellFilters.levelFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.properties, selectedProperties, spellFilters.propertyFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.source_book, selectedSourceBooks, spellFilters.sourceBookFilters)
                }
            }
        }
    }
}

// Experimental api is for the keyboard controller
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpellsSearchBar(viewModel: SpellsScreenViewModel) {
    val searchBarText by viewModel.searchBarText.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchBarText,
        onValueChange = viewModel::onSearchBarTextChange,
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
            IconButton(onClick = { viewModel.setFilterDialogVisibilityStateTo(true) }) {
                Icon(Icons.Filled.Menu, contentDescription = "Filter spells")
            }
        }
    )
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
