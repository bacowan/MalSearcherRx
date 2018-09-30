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
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartAnimeSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartCharacterSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartLanguageSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.ViewModel.LoginViewModel
import moe.cowan.brendan.malsearcherrx.ViewModel.SearchViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var disposables = CompositeDisposable()

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    @Override
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Override
    override fun onStart() {
        super.onStart()
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }
        setupStreams()
    }

    @Override
    override fun onStop() {
        super.onStop()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun setupStreams() {
        val vm = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]

        val uiEvents = setupUiEvents()
        val (models, posts) = vm.SubscribeTo(uiEvents)

        disposables.add( models.subscribe { updateUI(it) } )
        disposables.add( posts.subscribe { updateUI(it) } )
    }

    private fun setupUiEvents() : Observable<SearchUIEvent> {
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

    private fun updateUI(model: SearchUIModel) {

    }

    private fun updateUI(post: SearchUIPost) {
        when (post) {
            is ShowAnimeSearch -> AnimeSearchDialog().show(fragmentManager, "")
        }
    }

    private fun loadAnimeSearch() {

    }

}