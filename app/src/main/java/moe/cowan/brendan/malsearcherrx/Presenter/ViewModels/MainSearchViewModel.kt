package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import moe.cowan.brendan.malsearcherrx.Model.DataModels.UserAnimeList
import moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers.UserAnimeListTransformer
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.RetrieveUserListAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.UserAnimeListResult
import moe.cowan.brendan.malsearcherrx.Utilities.Optional
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.MainSearchUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.MainSearchUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowCharacterSearch
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowLanguageSearch
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.*
import javax.inject.Inject

class MainSearchViewModel @Inject constructor(): SubscribableViewModel<MainSearchUIEvent, MainSearchUIModel, MainSearchUIPost>() {

    @Inject
    lateinit var userAnimeListTransformer: UserAnimeListTransformer

    private val previousModelSubject: BehaviorSubject<Optional<MainSearchUIModel>> = BehaviorSubject.create()
    private val userListSubject: BehaviorSubject<UserAnimeList> = BehaviorSubject.create()

    override fun subscribe(events: Observable<MainSearchUIEvent>) : Pair<Observable<MainSearchUIModel>?, Observable<MainSearchUIPost>?> {
        val results = events.publish { shared -> Observable.merge( listOf(

                shared.ofType(StartAnimeSearchEvent::class.java).map { ShowAnimeSearch() as MainSearchUIPost },

                shared.ofType(StartCharacterSearchEvent::class.java).withLatestFrom(previousModelSubject.startWith(Optional.empty()))
                    { _, previousModel -> ShowCharacterSearch(previousModel.flatMap { it.anime.map { it.anime } }) as MainSearchUIPost },

                shared.ofType(StartLanguageSearchEvent::class.java).withLatestFrom(
                        previousModelSubject.filter { it.hasValue }.map { it.get() }.filter { it.character.hasValue }.map { it.character.get() })
                            { _, character -> ShowLanguageSearch( character.character ) },

                shared.ofType(SearchResultEvent::class.java),

                shared.ofType(SetUserEvent::class.java).map { RetrieveUserListAction(it.username) }.compose(userAnimeListTransformer)
                ))
        }.share()

        val uiModels = results.ofType(SearchResultEvent::class.java).scan(MainSearchUIModel(anime = Optional.empty(), character = Optional.empty(), language = Optional.empty()))
                    { previous, current ->
                        when (current) {
                            is SearchAnimeResultEvent -> previous.copy(anime = Optional.of(current.results))
                            is SearchCharacterResultEvent -> previous.copy(character = Optional.of(current.results))
                            is SearchLanguageResultEvent -> previous.copy(language = Optional.of(current.results))
                            else -> previous
                        }
                    }

        uiModels.map { Optional.of(it) }.subscribe(previousModelSubject)
        results.ofType(UserAnimeListResult::class.java).map { e -> e.Anime }.subscribe(userListSubject)

        val posts = results.ofType(MainSearchUIPost::class.java)

        return Pair(uiModels, posts)
    }
}