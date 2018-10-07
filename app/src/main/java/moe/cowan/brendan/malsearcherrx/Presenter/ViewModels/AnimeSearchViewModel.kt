package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.AnimeSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers.AnimeSearchTransformer
import moe.cowan.brendan.malsearcherrx.View.UIEvents.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.SearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.SearchResultUIModel
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