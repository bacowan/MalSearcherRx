package moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search

interface SearchDialogUIModel {
        val inProgress: Boolean
        val searchHint: SearchHint
        val message: String
}

data class ImageTextSearchDialogUIModel(
        override val inProgress: Boolean,
        override val searchHint: SearchHint,
        val searchResults: List<ImageTextSearchResultUIModel> = listOf(),
        override val message: String = ""
): SearchDialogUIModel

data class TextSearchDialogUIModel(
        override val inProgress: Boolean,
        override val searchHint: SearchHint,
        val searchResults: List<TextSearchResultUIModel> = listOf(),
        override val message: String = ""
): SearchDialogUIModel