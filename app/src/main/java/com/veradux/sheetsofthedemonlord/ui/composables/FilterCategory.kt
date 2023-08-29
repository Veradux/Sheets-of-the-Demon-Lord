package com.veradux.sheetsofthedemonlord.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veradux.sheetsofthedemonlord.R

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(
    icon: ImageVector,
    @StringRes categoryNameResource: Int,
    allFilters: List<String>,
    selectedFilters: List<String>,
    onFilterSelected: (String) -> Unit
) {
    Column {
        Row {
            Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically))
            Text(
                text = stringResource(categoryNameResource),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp),
            )
        }

        FlowRow {
            allFilters.forEach { filter ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    label = { Text(filter) },
                    onClick = { onFilterSelected(filter) },
                    selected = selectedFilters.contains(filter),
                )
            }
        }
    }
}

@Preview
@Composable
fun FilterCategoryPreview() {
    val allFilters = rememberSaveable { listOf("One", "Two", "Three", "Four", "Five") }
    val selectedFilters = rememberSaveable { mutableStateListOf("Two") }

    FilterCategory(
        icon = Icons.Rounded.Check,
        categoryNameResource = R.string.spells,
        allFilters = allFilters,
        selectedFilters = selectedFilters,
        onFilterSelected = { filter ->
            if (selectedFilters.contains(filter))
                selectedFilters.remove(filter)
            else selectedFilters.add(filter)
        }
    )
}
