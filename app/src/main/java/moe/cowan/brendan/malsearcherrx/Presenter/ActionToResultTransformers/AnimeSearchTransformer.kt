package moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Model.Services.AnimeSearcher
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.AnimeSearchAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.AnimeSearchResult
import javax.inject.Inject

class AnimeSearchTransformer @Inject constructor(private val animeSearcher: AnimeSearcher) : ObservableTransformer<AnimeSearchAction, AnimeSearchResult> {
    @Override
    override fun apply(upstream: Observable<AnimeSearchAction>): ObservableSource<AnimeSearchResult> {
        return upstream
                .flatMap {
                    action -> animeSearcher.getMatchingAnime(action.searchString)
                        .map { response -> AnimeSearchResult(false, anime = response) }
                        .onErrorReturn { AnimeSearchResult(false, message = "oops") }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .startWith(AnimeSearchResult(true))
                }
    }
}