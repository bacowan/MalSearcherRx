package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.*
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SearchItemClickEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SetParentCharacterEvent
import javax.inject.Inject

class LanguageSearchViewModel @Inject constructor(): SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        val results = events.publish { shared -> Observable.merge(
                shared.ofType(SetParentCharacterEvent::class.java).map { it.character },
                shared.ofType(SearchItemClickEvent::class.java).map { SearchDialogUIPost(it.searchItem) }
        )}.share()

        val uiModels = results.ofType(AnimeCharacter::class.java).map{
            TextSearchDialogUIModel(false, SearchHint.Language, it.languages.map {
                LanguageSearchResultUIModel(it.name, it)
            }) as SearchDialogUIModel
        }

        val posts = results.ofType(SearchDialogUIPost::class.java)

        return Pair(uiModels, posts)
    }
}