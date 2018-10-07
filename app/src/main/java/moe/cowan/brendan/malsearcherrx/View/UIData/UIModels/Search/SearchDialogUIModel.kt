package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

data class SearchDialogUIModel(val inProgress: Boolean, val searchResults: List<SearchResultUIModel> = listOf(), val message: String = "")