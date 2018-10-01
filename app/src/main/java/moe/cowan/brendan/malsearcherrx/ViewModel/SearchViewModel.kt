package moe.cowan.brendan.malsearcherrx.ViewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.StartAnimeSearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.ShowAnimeSearch
import javax.inject.Inject

class SearchViewModel @Inject constructor(): SubscribableViewModel<SearchUIEvent, SearchUIModel, SearchUIPost>() {

    private val searchUISubject: BehaviorSubject<SearchUIModel> = BehaviorSubject.create()
    private val searchPostSubject: PublishSubject<SearchUIPost> = PublishSubject.create()

    init {
        val initialState = SearchUIModel()
        searchUISubject.onNext(initialState)
    }

    override fun SubscribeTo(events: Observable<SearchUIEvent>) : Pair<Observable<SearchUIModel>, Observable<SearchUIPost>> {
        val posts = events.publish {
            it.ofType(StartAnimeSearchEvent::class.java).map { _ -> ShowAnimeSearch() as SearchUIPost }
        }

        posts.subscribe(searchPostSubject)

        return Pair(searchUISubject, searchPostSubject)
    }
}