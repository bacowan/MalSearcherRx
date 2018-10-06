package moe.cowan.brendan.malsearcherrx.View

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.search_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartAnimeSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartCharacterSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartLanguageSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.ViewModel.MissingViewModelException
import moe.cowan.brendan.malsearcherrx.ViewModel.SearchViewModel
import moe.cowan.brendan.malsearcherrx.ViewModel.SubscribableViewModel
import java.lang.ClassCastException
import javax.inject.Inject

class SearchFragment :  ReactiveFragment<SearchUIEvent, SearchUIModel, SearchUIPost>() {

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
    override fun updateUIModel(model: SearchUIModel) {
    }

    @Override
    override fun updateUIPost(post: SearchUIPost) {
        when (post) {
            is ShowAnimeSearch -> AnimeSearchDialog().show(fragmentManager, "")
        }
    }

    private fun loadAnimeSearch() {

    }

}