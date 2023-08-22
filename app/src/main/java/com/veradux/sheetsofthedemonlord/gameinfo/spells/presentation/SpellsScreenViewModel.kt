package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersRepository
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersRepositoryMock
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsRepository
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsRepositoryMock
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SpellsScreenViewModel(
    spellsRepository: SpellsRepository = SpellsRepositoryMock(),
    spellFiltersRepository: SpellFiltersRepository = SpellFiltersRepositoryMock()
) : ViewModel() {

    private val allSpells = spellsRepository.getSpells()
    val allFilters = spellFiltersRepository.getSpellFilters()

    // states
    private val _isFilterDialogOpen = MutableStateFlow(false)
    val isFilterDialogOpen = _isFilterDialogOpen.asStateFlow()

    private val _filteredSpells = MutableStateFlow(allSpells)
    val filteredSpells = _filteredSpells.asStateFlow()

    private val _spellFilters = MutableStateFlow(allFilters)
    val spellFilters = _spellFilters.asStateFlow()

    private val _searchBarText = MutableStateFlow("")
    val searchBarText = _searchBarText.asStateFlow()

    fun onSearchBarTextChange(text: String) {
        _searchBarText.value = text
        _filteredSpells.value = getFilteredSpells()
    }

    fun setFilterDialogVisibilityStateTo(isVisible: Boolean) {
        _isFilterDialogOpen.value = isVisible
        if (!isVisible) {
            _filteredSpells.value = getFilteredSpells()
        }
    }

    private fun getFilteredSpells(): List<Spell> = allSpells.filter { spell ->
        spell.containsText(searchBarText.value) && spellFilters.value.filter(spell)
    }
}
