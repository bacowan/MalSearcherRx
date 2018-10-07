package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime

class AnimeSearchResultUIModel(val anime: Anime): SearchResultUIModel {
    override val title: String get() = anime.title
    override val imageUrl: String get() = anime.imageUrl
}