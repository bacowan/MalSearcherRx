package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.*
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SetParentCharacterEvent
import javax.inject.Inject

class LanguageSearchViewModel @Inject constructor(): SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    private val parentCharacterSubject: BehaviorSubject<ImageTextSearchResultUIModel> = BehaviorSubject.create()

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        val results = events.ofType(SetParentCharacterEvent::class.java)
                .map { it.character }.share()

        results.ofType(ImageTextSearchResultUIModel::class.java).subscribe(parentCharacterSubject)

        val uiModels = results.map { TextSearchDialogUIModel(false, SearchHint.Language, it.languages.map {
            LanguageSearchResultUIModel(it.name, it)
        }) as SearchDialogUIModel }

        return Pair(uiModels, null)
    }
}