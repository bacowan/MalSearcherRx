package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

data class SearchDialogUIModel(
        val inProgress: Boolean,
        val searchResults: List<SearchResultUIModel> = listOf(),
        val searchHint: SearchHint,
        val message: String = "")