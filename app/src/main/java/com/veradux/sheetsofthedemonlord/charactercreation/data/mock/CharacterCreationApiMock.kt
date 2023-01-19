package com.veradux.sheetsofthedemonlord.charactercreation.data.mock

import com.veradux.sheetsofthedemonlord.charactercreation.data.CharacterCreationApi
import com.veradux.sheetsofthedemonlord.charactercreation.model.Ancestry

// TODO maybe instead of having all game info loaded into the app and returning objects,
//  we can store it as json and read it only when necessary
class CharacterCreationApiMock : CharacterCreationApi {

    override fun getAncestries(): List<Ancestry> {
        return listOf(human, goblin)
    }

}
