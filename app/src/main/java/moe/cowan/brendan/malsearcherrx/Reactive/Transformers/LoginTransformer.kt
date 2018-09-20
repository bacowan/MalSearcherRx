package moe.cowan.brendan.malsearcherrx.Reactive.Transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Model.LoginService
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginAction
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginResult
import javax.inject.Inject

class LoginTransformer @Inject constructor(private val loginService: LoginService) : ObservableTransformer<LoginAction, LoginResult> {
    override fun apply(upstream: Observable<LoginAction>): ObservableSource<LoginResult> {
        return upstream
                .flatMap { event -> loginService.verifyLogin(event.username)
                .map { response -> LoginResult(Success = response, InProgress = false, Message = "success") }
                .onErrorReturn { _ -> LoginResult(Success = false, InProgress = false, Message = "oops") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .startWith(LoginResult(Success = false, InProgress = true)) }
    }
}