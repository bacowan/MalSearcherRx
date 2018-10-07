package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels

data class SearchDialogUIModel(val inProgress: Boolean, val anime: List<SearchResultUIModel> = listOf(), val message: String = "")