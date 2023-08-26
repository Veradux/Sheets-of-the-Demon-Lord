package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.veradux.sheetsofthedemonlord.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpellsSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    setActiveStateTo: (Boolean) -> Unit,
    setFilterDialogVisibilityStateTo: (Boolean) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
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
                AnimatedVisibility (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear the search bar.")
                    }
                }
                IconButton(
                    onClick = { setFilterDialogVisibilityStateTo(true) }) {
                    // TODO replace the placeholder icon with a filter icon
                    Icon(Icons.Filled.Menu, contentDescription = "Filter spells.")
                }
            }
        },
        content = content
    )
}
