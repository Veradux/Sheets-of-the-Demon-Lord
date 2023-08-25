package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories

@Composable
fun SpellsScreen(viewModel: SpellsScreenViewModel = viewModel()) {
    val isFilterDialogOpen by viewModel.isFilterDialogOpen.collectAsState(initial = false)
    val filteredSpells by viewModel.filteredSpells.collectAsState()
    val spellFilters by viewModel.spellFilters.collectAsState()

    Column {
        Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
            SpellsSearchBar(
                modifier = Modifier.weight(1f),
                searchBarText = viewModel.searchBarText.collectAsState().value,
                onSearchBarTextChange = viewModel::onSearchBarTextChange
            )
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { viewModel.setFilterDialogVisibilityStateTo(true) }) {
                Icon(Icons.Filled.Menu, contentDescription = "Filter spells")
            }
        }
        if (filteredSpells.isEmpty())
            Text(
                text = stringResource(R.string.no_spells_found),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            )
        else {
            LazyColumn {
                items(filteredSpells) { Spell(it) }
            }
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
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { viewModel.setFilterDialogVisibilityStateTo(false) }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Filter spells")
                    }

                    FilterCategory(R.string.traditions, Icons.Default.Star, spellFilters.traditionFilters) { toggle ->
                        viewModel.updateSpellFilters(spellFilters.traditionFilters, toggle) {
                            spellFilters.copy(traditionFilters = it)
                        }
                    }
                    Divider(modifier = Modifier.padding(8.dp))
                    FilterCategory(R.string.level, Icons.Default.Star, spellFilters.levelFilters) { toggle ->
                        viewModel.updateSpellFilters(spellFilters.levelFilters, toggle) {
                            spellFilters.copy(levelFilters = it)
                        }
                    }

                    Divider(modifier = Modifier.padding(8.dp))
                    FilterCategory(R.string.properties, Icons.Default.Star, spellFilters.propertyFilters) { toggle ->
                        viewModel.updateSpellFilters(spellFilters.propertyFilters, toggle) {
                            spellFilters.copy(propertyFilters = it)
                        }
                    }
                    Divider(modifier = Modifier.padding(8.dp))
                    FilterCategory(R.string.source_book, Icons.Default.Star, spellFilters.sourceBookFilters) { toggle ->
                        viewModel.updateSpellFilters(spellFilters.sourceBookFilters, toggle) {
                            spellFilters.copy(sourceBookFilters = it)
                        }
                    }
                }
            }
        }
    }
}

// Experimental api is for the keyboard controller
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpellsSearchBar(modifier: Modifier, searchBarText: String, onSearchBarTextChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier,
        value = searchBarText,
        onValueChange = onSearchBarTextChange,
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
            if (searchBarText.isNotEmpty()) {
                IconButton(onClick = { onSearchBarTextChange("") }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear the search bar.")
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(
    @StringRes categoryNameResource: Int,
    icon: ImageVector,
    filters: List<SpellFilterCategories.Filter>,
    onClick: (SpellFilterCategories.Filter) -> Unit
) {
    Row {
        Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically))
        Text(
            text = stringResource(categoryNameResource),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
        )
    }

    FlowRow {
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                label = { Text(filter.name) },
                onClick = { onClick(filter) },
                selected = filter.isEnabled,
            )
        }
    }
}
