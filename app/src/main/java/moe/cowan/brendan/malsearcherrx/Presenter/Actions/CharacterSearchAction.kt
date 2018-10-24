package moe.cowan.brendan.malsearcherrx.Presenter.Actions

import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Utilities.Optional

data class CharacterSearchAction(val searchString: String, val anime: Optional<Anime>)