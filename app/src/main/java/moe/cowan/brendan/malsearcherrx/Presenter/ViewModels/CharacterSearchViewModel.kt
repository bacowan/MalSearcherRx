package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers.CharacterSearchTransformer
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.CharacterSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.CharacterSearchResult
import moe.cowan.brendan.malsearcherrx.Utilities.Optional
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchHint
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchItemClickEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SetParentAnimeEvent
import javax.inject.Inject

class CharacterSearchViewModel @Inject constructor(): SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    @Inject
    lateinit var characterSearchTransformer: CharacterSearchTransformer

    private val parentAnimeSubject: BehaviorSubject<Optional<SearchResultUIModel>> = BehaviorSubject.create()

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        val results = events.publish { shared -> Observable.merge(
                shared.ofType(SearchEvent::class.java).withLatestFrom(parentAnimeSubject.startWith(Optional.empty()))
                        { searchEvent, anime -> CharacterSearchAction(searchEvent.searchString, anime.map { it.databaseId }) }
                        .compose(characterSearchTransformer),
                shared.ofType(SearchItemClickEvent::class.java)
                        .map { SearchDialogUIPost(it.searchItem) },
                shared.ofType(SetParentAnimeEvent::class.java)
                        .map { it.anime })
        }.share()

        results.ofType(SearchResultUIModel::class.java).map { Optional.of(it) }.subscribe(parentAnimeSubject)

        val uiModels = results.ofType(CharacterSearchResult::class.java)
                .scan(SearchDialogUIModel(inProgress = false, searchResults = listOf(), searchHint = SearchHint.Character)) { _, current ->
                SearchDialogUIModel(
                        inProgress = current.inProgress,
                        searchResults = current.characters.map { character -> SearchResultUIModel(character.name, character.imageUrl, character.databaseId) },
                        searchHint = SearchHint.Character,
                        message = current.message)
        }

        val uiPosts = results.ofType(SearchDialogUIPost::class.java)

        return Pair(uiModels, uiPosts)
    }
}