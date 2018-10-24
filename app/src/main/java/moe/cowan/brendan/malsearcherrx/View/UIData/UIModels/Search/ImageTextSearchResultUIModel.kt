package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Language
import java.io.Serializable

interface ImageTextSearchResultUIModel: Serializable {
    val title: String
    val imageUrl: String
}

interface  TextSearchResultUIModel: Serializable {
    val text: String
}

data class AnimeSearchResultUIModel(override val title: String, override val imageUrl: String, val anime: Anime): ImageTextSearchResultUIModel
data class CharacterSearchResultUIModel(override val title: String, override val imageUrl: String, val character: AnimeCharacter): ImageTextSearchResultUIModel
data class LanguageSearchResultUIModel(override val text: String, val language: Language): TextSearchResultUIModel