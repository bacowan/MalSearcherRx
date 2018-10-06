package moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search

data class SearchDialogUIModel(val inProgress: Boolean, val anime: List<SearchResultUIModel> = listOf(), val message: String = "")