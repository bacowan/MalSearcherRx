package moe.cowan.brendan.malsearcherrx.View.Fragments

import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.search_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.MainSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.StartAnimeSearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.StartCharacterSearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.StartLanguageSearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.MainSearchUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.MainSearchUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.AnimeSearchViewModel
import moe.cowan.brendan.malsearcherrx.View.Dialogs.SearchDialog
import javax.inject.Inject

class MainSearchFragment :  ReactiveFragment<MainSearchUIEvent, MainSearchUIModel, MainSearchUIPost>() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override val layout get() = R.layout.search_fragment

    @Override
    override fun setupUiEvents() : Observable<MainSearchUIEvent> {
        val searchAnimeEvents = RxView.clicks(anime_search_button)
                .map { _ -> StartAnimeSearchEvent() as MainSearchUIEvent }

        val searchCharacterEvents = RxView.clicks(character_search_button)
                .map { _ -> StartCharacterSearchEvent() as MainSearchUIEvent }

        val searchLanguageEvents = RxView.clicks(language_search_button)
                .map { _ -> StartLanguageSearchEvent() as MainSearchUIEvent }

        return searchAnimeEvents
                .mergeWith(searchCharacterEvents)
                .mergeWith(searchLanguageEvents)
    }

    @Override
    override fun updateUIPost(post: MainSearchUIPost) {
        when (post) {
            is ShowAnimeSearch -> fragmentFactory.createDialogFragment<SearchDialog, AnimeSearchViewModel>().show(fragmentManager, "")
        }
    }

    @Override
    override fun updateUIModel(model: MainSearchUIModel) {
        if (model.anime == null) {
            anime_search_button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0)
        }
        else {

        }

        if (model.character == null) {
            character_search_button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0)
        }
        else {

        }

        if (model.language == null) {
            language_search_button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0)
        }
        else {

        }

    }

}