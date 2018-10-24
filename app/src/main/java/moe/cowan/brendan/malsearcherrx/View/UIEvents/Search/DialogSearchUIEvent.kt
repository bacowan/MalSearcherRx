package moe.cowan.brendan.malsearcherrx.View.UIEvents.Search

import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.TextSearchResultUIModel
import java.io.Serializable

interface DialogSearchUIEvent
interface SearchItemClickEvent: DialogSearchUIEvent { val searchItem: Serializable }

data class ImageTextSearchItemClickEvent(override val searchItem: ImageTextSearchResultUIModel): SearchItemClickEvent
data class TextSearchItemClickEvent(override val searchItem: TextSearchResultUIModel): SearchItemClickEvent

data class SearchEvent(val searchString: String): DialogSearchUIEvent


data class SetParentAnimeEvent(val anime: Anime): DialogSearchUIEvent
data class SetParentCharacterEvent(val character: AnimeCharacter): DialogSearchUIEvent