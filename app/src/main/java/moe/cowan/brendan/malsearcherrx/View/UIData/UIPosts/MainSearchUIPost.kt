package moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts

import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.Utilities.Optional

interface MainSearchUIPost

class ShowAnimeSearch : MainSearchUIPost
data class ShowCharacterSearch(val parentAnime: Optional<Anime>) : MainSearchUIPost
data class ShowLanguageSearch(val parentCharacter: AnimeCharacter) : MainSearchUIPost

data class MainSearchErrorPost(val message: String): MainSearchUIPost