package moe.cowan.brendan.malsearcherrx.View.UIEvents.Search

import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.AnimeSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.CharacterSearchResultUIModel

interface MainSearchUIEvent
interface SearchResultEvent: MainSearchUIEvent

class StartAnimeSearchEvent : MainSearchUIEvent
class StartCharacterSearchEvent : MainSearchUIEvent
class StartLanguageSearchEvent : MainSearchUIEvent
data class SearchAnimeResultEvent(val results: AnimeSearchResultUIModel) : SearchResultEvent
data class SearchCharacterResultEvent(val results: CharacterSearchResultUIModel) : SearchResultEvent