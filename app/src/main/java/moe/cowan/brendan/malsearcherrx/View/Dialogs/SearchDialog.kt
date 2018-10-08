package moe.cowan.brendan.malsearcherrx.View.Dialogs

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_dialog.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchItemClickEvent

class SearchDialog : ReactiveDialog<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    override val layout: Int get() = R.layout.search_dialog

    private val itemClickSubject: PublishSubject<DialogSearchUIEvent> = PublishSubject.create()

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val resultsView = view?.findViewById<RecyclerView>(R.id.results_list)
        resultsView?.layoutManager = LinearLayoutManager(activity)
        resultsView?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        return view
    }

    @Override
    override fun onResume() {
        super.onResume()
    }

    @Override
    override fun setupUiEvents(): Observable<DialogSearchUIEvent> {
        val searchEvents = RxTextView.editorActionEvents(edit_anime_title)
                .filter { event -> event.actionId() == EditorInfo.IME_ACTION_SEARCH }
                .doOnNext { hideKeyboard() }
                .map { SearchEvent(edit_anime_title.text.toString()) as DialogSearchUIEvent }

        return searchEvents.mergeWith(itemClickSubject)
    }

    @Override
    override fun updateUIModel(model: SearchDialogUIModel) {
        progress_bar_layout.visibility = when (model.inProgress) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        results_list.adapter = SearchResultsAdapter(model.searchResults, View.OnClickListener
            { v -> itemClickSubject.onNext(SearchItemClickEvent(v)) })
    }

    @Override
    override fun updateUIPost(post: SearchDialogUIPost) {
        dismiss()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}