package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.Utilities.Optional

data class MainSearchUIModel(
        val anime: Optional<AnimeSearchResultUIModel>,
        val character: Optional<CharacterSearchResultUIModel>,
        val language: Optional<String>)