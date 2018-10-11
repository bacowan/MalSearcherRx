package moe.cowan.brendan.malsearcherrx.Presenter.Results

import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter

data class CharacterSearchResult(val inProgress: Boolean, val characters: List<AnimeCharacter> = listOf(), val message: String = "")