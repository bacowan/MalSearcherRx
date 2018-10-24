package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.SearchDialogUIPost
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.SetParentCharacterEvent
import javax.inject.Inject

class LanguageSearchViewModel @Inject constructor(): SubscribableViewModel<DialogSearchUIEvent, SearchDialogUIModel, SearchDialogUIPost>() {

    private val parentCharacterSubject: BehaviorSubject<ImageTextSearchResultUIModel> = BehaviorSubject.create()

    @Override
    override fun subscribe(events: Observable<DialogSearchUIEvent>): Pair<Observable<SearchDialogUIModel>?, Observable<SearchDialogUIPost>?> {
        TODO()
        /*val results = events.ofType(SetParentCharacterEvent::class.java)
                .map { it.character }

        results.ofType(ImageTextSearchResultUIModel::class.java).subscribe(parentCharacterSubject)

        val uiModels = results.ofType*/
    }
}