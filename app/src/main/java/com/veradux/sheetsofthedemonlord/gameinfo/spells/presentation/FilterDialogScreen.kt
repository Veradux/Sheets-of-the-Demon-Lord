package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories
import com.veradux.sheetsofthedemonlord.ui.composables.FilterCategory

@Composable
fun FilterDialogScreen(
    filterCategories: List<FilterCategory>,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.filter_dialog_screen_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                }

                filterCategories.forEach { category ->
                    FilterCategory(category)

                    if (filterCategories.last() != category) {
                        Divider(Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FilterDialogPreview() {
    FilterDialogScreen(
        filterCategories = SpellFilterCategories().getFilterCategories(),
        onDismiss = { }
    )
}
