package moe.cowan.brendan.malsearcherrx.View.UIEvents.Search

interface DialogSearchUIEvent

data class SearchEvent(val searchString: String): DialogSearchUIEvent
data class SearchItemClickEvent<T>(val searchItem: T): DialogSearchUIEvent