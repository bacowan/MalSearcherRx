package moe.cowan.brendan.malsearcherrx.Reactive.Transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Model.LoginService
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIModel
import javax.inject.Inject

class LoginTransformer @Inject constructor(private val loginService: LoginService) : ObservableTransformer<LoginUIEvent, LoginUIModel> {
    override fun apply(upstream: Observable<LoginUIEvent>): ObservableSource<LoginUIModel> {
        return upstream
                .flatMap { event -> loginService.verifyLogin(event.username)
                .map { response -> LoginUIModel(Success = response, InProgress = false, Message = "success") }
                .onErrorReturn { _ -> LoginUIModel(Success = false, InProgress = false, Message = "oops") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .startWith(LoginUIModel(Success = false, InProgress = true)) }
    }
}