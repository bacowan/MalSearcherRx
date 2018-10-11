package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers.CharacterSearchTransformer
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.CharacterSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.CharacterSearchResult
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchItemClickEvent
import javax.inject.Inject

class CharacterSearchViewModel: SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    @Inject
    lateinit var characterSearchTransformer: CharacterSearchTransformer

    var anime: SearchResultUIModel? = null

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        val results = events.publish { shared -> Observable.merge(
                shared.ofType(SearchEvent::class.java)
                        .map { CharacterSearchAction(it.searchString, anime?.databaseId) }.compose(characterSearchTransformer),
                shared.ofType(SearchItemClickEvent::class.java)
                        .map { SearchDialogUIPost(it.searchItem) })
        }.share()

        val uiModels = results.scan(SearchDialogUIModel(inProgress = false, searchResults = listOf(), message = "")) { previous, current ->
            if (current is CharacterSearchResult) {
                SearchDialogUIModel(
                        inProgress = current.inProgress,
                        searchResults = current.characters.map { character -> SearchResultUIModel(character.name, character.imageUrl, character.databaseId) },
                        message = current.message)
            }
            else {
                previous
            }
        }

        val uiPosts = results.ofType(SearchDialogUIPost::class.java)

        return Pair(uiModels, uiPosts)
    }
}