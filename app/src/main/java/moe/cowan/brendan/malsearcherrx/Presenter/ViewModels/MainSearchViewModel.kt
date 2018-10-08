package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.AnimeSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.AnimeSearchResult
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.MainSearchUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.MainSearchUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.*
import javax.inject.Inject

class MainSearchViewModel @Inject constructor(): SubscribableViewModel<MainSearchUIEvent, MainSearchUIModel, MainSearchUIPost>() {

    override fun subscribe(events: Observable<MainSearchUIEvent>) : Pair<Observable<MainSearchUIModel>?, Observable<MainSearchUIPost>?> {
        val results = events.publish { shared -> Observable.merge(
                shared.ofType(StartAnimeSearchEvent::class.java).map { ShowAnimeSearch() as MainSearchUIPost },
                shared.ofType(SearchAnimeResultEvent::class.java))
        }.share()

        val uiModels = results.scan(MainSearchUIModel(anime = null, character = null, language = null)) { previous, current ->
            when (current) {
                is SearchAnimeResultEvent -> previous.copy(anime = current.results)
                else -> previous
            }
        }

        val posts = results.ofType(MainSearchUIPost::class.java)

        return Pair(uiModels, posts)
    }
}