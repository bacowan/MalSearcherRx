package moe.cowan.brendan.malsearcherrx.View.Dialogs

import android.support.v7.widget.RecyclerView
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent

interface SearchResultsAdapterFactory<T: SearchDialogUIModel, U> {
    fun newAdapter(model: T, clickListener: ClickListener<U>): RecyclerView.Adapter<*>
    fun clickEvent(clickedItem: U): DialogSearchUIEvent
}