package moe.cowan.brendan.malsearcherrx.View.UIEvents.Search

import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel

interface DialogSearchUIEvent

data class SearchEvent(val searchString: String): DialogSearchUIEvent
data class SearchItemClickEvent(val searchItem: SearchResultUIModel): DialogSearchUIEvent

data class SetParentAnimeEvent(val anime: SearchResultUIModel): DialogSearchUIEvent