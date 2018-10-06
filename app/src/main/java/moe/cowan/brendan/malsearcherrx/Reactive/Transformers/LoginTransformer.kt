package moe.cowan.brendan.malsearcherrx.Reactive.Transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Model.Services.LoginService
import moe.cowan.brendan.malsearcherrx.Reactive.Actions.LoginAction
import moe.cowan.brendan.malsearcherrx.Reactive.Results.LoginResult
import javax.inject.Inject

class LoginTransformer @Inject constructor(private val loginService: LoginService) : ObservableTransformer<LoginAction, LoginResult> {
    @Override
    override fun apply(upstream: Observable<LoginAction>): ObservableSource<LoginResult> {
        return upstream
                .flatMap { event -> loginService.verifyLogin(event.username)
                    .map { response -> LoginResult(
                            SuccessfulUsername = if (response) event.username else null,
                            InProgress = false,
                            Message = "success") }
                    .onErrorReturn { LoginResult(SuccessfulUsername = null, InProgress = false, Message = "oops") }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .startWith(LoginResult(SuccessfulUsername = null, InProgress = true)) }
    }
}