package moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts

import moe.cowan.brendan.malsearcherrx.Utilities.Optional
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel

interface MainSearchUIPost

class ShowAnimeSearch : MainSearchUIPost
data class ShowCharacterSearch(val parentAnime: Optional<SearchResultUIModel>) : MainSearchUIPost
data class ShowLanguageSearch(val parentCharacter: SearchResultUIModel) : MainSearchUIPost

data class MainSearchErrorPost(val message: String): MainSearchUIPost