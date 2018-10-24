package moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Model.Services.CharacterSearcher
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.CharacterSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.CharacterSearchResult
import javax.inject.Inject

class CharacterSearchTransformer @Inject constructor(private val characterSearcher: CharacterSearcher) : ObservableTransformer<CharacterSearchAction, CharacterSearchResult> {
    @Override
    override fun apply(upstream: Observable<CharacterSearchAction>): ObservableSource<CharacterSearchResult> {
        return upstream.flatMap {
            action -> characterSearcher.getMatchingCharacter(action.searchString, action.anime)
                .map { response -> CharacterSearchResult(false, characters = response) }
                .onErrorReturn { CharacterSearchResult(false, message = "oops") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .startWith(CharacterSearchResult(true))
        }
    }
}