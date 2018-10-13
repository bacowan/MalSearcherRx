package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

import moe.cowan.brendan.malsearcherrx.Utilities.Optional

data class MainSearchUIModel(
        val anime: Optional<SearchResultUIModel>,
        val character: Optional<SearchResultUIModel>,
        val language: Optional<String>)