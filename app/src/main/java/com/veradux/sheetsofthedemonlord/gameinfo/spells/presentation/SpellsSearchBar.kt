package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpellsSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    setActiveStateTo: (Boolean) -> Unit,
    onFilterButtonClick: () -> Unit,
    spells: List<Spell>
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { setActiveStateTo(false) },
        active = isActive,
        onActiveChange = setActiveStateTo,
        placeholder = { Text(text = stringResource(R.string.spell_search_hint)) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search bar.") },
        trailingIcon = {
            Row {
                AnimatedVisibility(query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear the search bar.")
                    }
                }
                IconButton(onClick = onFilterButtonClick) {
                    // TODO replace the placeholder icon with a filter icon
                    Icon(Icons.Filled.Menu, contentDescription = "Filter spells.")
                }
            }
        },
        content = {
            Text(
                text = stringResource(R.string.x_spells_found, spells.count()),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .align(Alignment.End)
            )
            LazyColumn {
                items(spells) { spell ->
                    SpellTitle(spell, modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp))
                }
            }
        }
    )
}
