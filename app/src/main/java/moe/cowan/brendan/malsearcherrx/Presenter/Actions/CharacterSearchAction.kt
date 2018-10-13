package moe.cowan.brendan.malsearcherrx.Presenter.Actions

import moe.cowan.brendan.malsearcherrx.Utilities.Optional
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel

data class CharacterSearchAction(val searchString: String, val animeId: Optional<Long>)