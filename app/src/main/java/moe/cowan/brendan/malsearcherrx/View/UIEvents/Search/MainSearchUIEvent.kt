package moe.cowan.brendan.malsearcherrx.View.UIEvents.Search

import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel

interface MainSearchUIEvent

class StartAnimeSearchEvent : MainSearchUIEvent
class StartCharacterSearchEvent : MainSearchUIEvent
class StartLanguageSearchEvent : MainSearchUIEvent
data class SearchAnimeResultEvent(val results: SearchResultUIModel) : MainSearchUIEvent