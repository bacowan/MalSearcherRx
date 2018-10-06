package moe.cowan.brendan.malsearcherrx.View

import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.search_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartAnimeSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartCharacterSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartLanguageSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.ViewModel.AnimeSearchViewModel
import javax.inject.Inject

class SearchFragment :  ReactiveFragment<SearchUIEvent, SearchUIModel, SearchUIPost>() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override val layout get() = R.layout.search_fragment

    @Override
    override fun setupUiEvents() : Observable<SearchUIEvent> {
        val searchAnimeEvents = RxView.clicks(anime_search_button)
                .map { _ -> StartAnimeSearchEvent() as SearchUIEvent }

        val searchCharacterEvents = RxView.clicks(character_search_button)
                .map { _ -> StartCharacterSearchEvent() as SearchUIEvent }

        val searchLanguageEvents = RxView.clicks(language_search_button)
                .map { _ -> StartLanguageSearchEvent() as SearchUIEvent }

        return searchAnimeEvents
                .mergeWith(searchCharacterEvents)
                .mergeWith(searchLanguageEvents)
    }

    @Override
    override fun updateUIPost(post: SearchUIPost) {
        when (post) {
            is ShowAnimeSearch -> fragmentFactory.createDialogFragment<SearchDialog, AnimeSearchViewModel>().show(fragmentManager, "")
        }
    }

}