package com.veradux.sheetsofthedemonlord.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veradux.sheetsofthedemonlord.R

@Stable
class Filter(
    val name: String,
    isEnabled: Boolean = false,
) {
    val isEnabled = mutableStateOf(isEnabled)
}

class FilterCategory(
    @StringRes val nameRes: Int,
    val icon: ImageVector,
    filters: List<String>
) {
    val filters = filters.map { Filter(it) }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(category: FilterCategory) {
    Column {
        Row {
            Icon(category.icon, contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically))
            Text(
                text = stringResource(category.nameRes),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp),
            )
        }

        FlowRow {
            category.filters.forEach { filter ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    label = { Text(filter.name) },
                    onClick = { filter.isEnabled.value = !filter.isEnabled.value },
                    selected = filter.isEnabled.value,
                )
            }
        }
    }
}

@Preview
@Composable
fun FilterCategoryPreview() {
    FilterCategory(
        FilterCategory(
            nameRes = R.string.properties,
            icon = Icons.Rounded.Star,
            filters = listOf("Requirement", "Target", "Area", "Duration")
        )
    )
}
