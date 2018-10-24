package moe.cowan.brendan.malsearcherrx.View.Dialogs

import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchResultUIModel

interface ClickListener<T> {
    fun onClick(item: T)
}