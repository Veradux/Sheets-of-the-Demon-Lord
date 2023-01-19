package com.veradux.sheetsofthedemonlord.charactercreation.data.mockdata

import com.veradux.sheetsofthedemonlord.charactercreation.data.CharacterCreationApi
import com.veradux.sheetsofthedemonlord.charactercreation.model.Ancestry
import com.veradux.sheetsofthedemonlord.charactercreation.model.AttributeScores
import com.veradux.sheetsofthedemonlord.charactercreation.model.CharacterSize.Medium
import com.veradux.sheetsofthedemonlord.charactercreation.model.CharacterSize.Small
import com.veradux.sheetsofthedemonlord.charactercreation.model.Feature
import com.veradux.sheetsofthedemonlord.charactercreation.model.Language

// TODO maybe instead of having all game info loaded into the app and returning objects,
//  we can store it as json and read it only when necessary
class CharacterCreationApiMock : CharacterCreationApi {

    override fun getAncestries(): List<Ancestry> {
        return listOf(human, goblin)
    }

}
