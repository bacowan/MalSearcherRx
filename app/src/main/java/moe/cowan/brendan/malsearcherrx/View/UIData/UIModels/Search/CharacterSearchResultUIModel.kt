package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter

class CharacterSearchResultUIModel(val character: AnimeCharacter) : SearchResultUIModel {
    override val imageUrl: String get() = character.imageUrl
    override val title: String get() = character.name
}