package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import android.util.Log
import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.AnimeSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers.AnimeSearchTransformer
import moe.cowan.brendan.malsearcherrx.Presenter.Results.AnimeSearchResult
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.AnimeSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchItemClickEvent
import javax.inject.Inject

class AnimeSearchViewModel @Inject constructor() : SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>(
        SearchDialogUIModel(inProgress = false, searchResults = listOf(), message = "")
) {

    @Inject
    lateinit var animeSearchTransformer: AnimeSearchTransformer

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        val results = events.publish { shared -> Observable.merge(
                shared.ofType(SearchEvent::class.java)
                        .map { AnimeSearchAction(it.searchString) }.compose(animeSearchTransformer),
                shared.ofType(SearchItemClickEvent::class.java)
                        .map { SearchDialogUIPost() })
        }.share()

        val uiModels = results.ofType(AnimeSearchResult::class.java).map {
            SearchDialogUIModel(inProgress = it.inProgress, searchResults = it.anime.map { anime -> AnimeSearchResultUIModel(anime) }, message = it.message)
        }

        val uiPosts = results.ofType(SearchDialogUIPost::class.java)

        return Pair(uiModels, uiPosts)
    }
}