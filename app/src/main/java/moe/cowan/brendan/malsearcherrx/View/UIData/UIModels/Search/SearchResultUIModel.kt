package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import java.io.Serializable

interface SearchResultUIModel: Serializable {
    val title: String
    val imageUrl: String
}

data class AnimeSearchResultUIModel(override val title: String, override val imageUrl: String, val anime: Anime): SearchResultUIModel
data class CharacterSearchResultUIModel(override val title: String, override val imageUrl: String, val character: AnimeCharacter): SearchResultUIModel