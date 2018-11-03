package moe.cowan.brendan.malsearcherrx.Presenter.Results

import moe.cowan.brendan.malsearcherrx.Model.DataModels.UserAnimeList

data class UserAnimeListResult(val InProgress: Boolean, val Anime: UserAnimeList?, val Message: String = "")