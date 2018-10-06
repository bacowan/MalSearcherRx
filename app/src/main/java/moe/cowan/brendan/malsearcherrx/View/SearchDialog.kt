package moe.cowan.brendan.malsearcherrx.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.search_dialog.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchDialogUIPost

class SearchDialog : ReactiveDialog<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    override val layout: Int get() = R.layout.search_dialog

    @Override
    override fun onResume() {
        super.onResume()
        dialog.window.setLayout(
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels)
    }

    @Override
    override fun setupUiEvents(): Observable<DialogSearchUIEvent> {
        return RxTextView.editorActionEvents(edit_anime_title)
                .filter { event -> event.actionId() == EditorInfo.IME_ACTION_SEARCH }
                .doOnNext { hideKeyboard() }
                .map { SearchEvent(edit_anime_title.text.toString()) as DialogSearchUIEvent }
    }

    @Override
    override fun updateUIModel(model: SearchDialogUIModel) {
        progress_bar_layout.visibility = when (model.inProgress) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}