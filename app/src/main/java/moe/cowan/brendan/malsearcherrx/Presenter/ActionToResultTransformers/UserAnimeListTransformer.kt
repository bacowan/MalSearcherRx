package moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Model.Services.UserListService
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.RetrieveUserListAction
import moe.cowan.brendan.malsearcherrx.Presenter.Results.UserAnimeListResult
import javax.inject.Inject

class UserAnimeListTransformer @Inject constructor(private val userListService: UserListService) : ObservableTransformer<RetrieveUserListAction, UserAnimeListResult> {

    override fun apply(upstream: Observable<RetrieveUserListAction>): ObservableSource<UserAnimeListResult> {
        return upstream
                .flatMap { event -> userListService.getUserList(event.username)
                    .map { UserAnimeListResult(
                            Anime = it,
                            InProgress = false)
                    }
                        .onErrorReturn { UserAnimeListResult(Anime = null, InProgress = false, Message = "oops") }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .startWith(UserAnimeListResult(Anime = null, InProgress = true)) }

    }
}