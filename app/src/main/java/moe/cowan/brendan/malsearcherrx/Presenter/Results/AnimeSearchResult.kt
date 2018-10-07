package moe.cowan.brendan.malsearcherrx.Presenter.Results

import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime

data class AnimeSearchResult(val inProgress: Boolean, val anime: List<Anime> = listOf(), val message: String = "")