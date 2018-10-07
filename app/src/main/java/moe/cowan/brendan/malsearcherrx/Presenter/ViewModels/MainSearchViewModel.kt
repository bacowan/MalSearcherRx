package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.View.UIEvents.MainSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.StartAnimeSearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.MainSearchUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.MainSearchUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowAnimeSearch
import javax.inject.Inject

class MainSearchViewModel @Inject constructor(): SubscribableViewModel<MainSearchUIEvent, MainSearchUIModel, MainSearchUIPost>(
        MainSearchUIModel(anime = null, character = null, language = null)
) {

    override fun subscribe(events: Observable<MainSearchUIEvent>) : Pair<Observable<MainSearchUIModel>?, Observable<MainSearchUIPost>?> {
        val posts = events.publish { event ->
            event.ofType(StartAnimeSearchEvent::class.java).map { ShowAnimeSearch() as MainSearchUIPost }
        }
        return Pair(null, posts)
    }
}