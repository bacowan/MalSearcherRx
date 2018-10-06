package moe.cowan.brendan.malsearcherrx.ViewModel

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Reactive.Actions.AnimeSearchAction
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.AnimeSearchTransformer
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Search.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchResultUIModel
import javax.inject.Inject

class AnimeSearchViewModel @Inject constructor() : SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>(
        SearchDialogUIModel(inProgress = false, anime = listOf(), message = "")
) {

    @Inject
    lateinit var animeSearchTransformer: AnimeSearchTransformer

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        val results = events.publish {
            shared -> shared.ofType(SearchEvent::class.java)
                .map { AnimeSearchAction(it.searchString) }.compose(animeSearchTransformer)
        }
        val uiModels = results.map {
            SearchDialogUIModel(inProgress = it.inProgress, anime = it.anime.map { anime -> SearchResultUIModel(anime.title, anime.imageUrl) }, message = it.message)
        }

        return Pair(uiModels, null)
    }
}