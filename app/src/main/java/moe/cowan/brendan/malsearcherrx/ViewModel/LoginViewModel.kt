package moe.cowan.brendan.malsearcherrx.ViewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.LoginTransformer
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginAction
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var loginTransformer: LoginTransformer

    private val loginUISubject: BehaviorSubject<LoginUIModel> = BehaviorSubject.create()

    init {
        val initialState = LoginUIModel(InProgress = false, Success = false, Message = "")
        loginUISubject.onNext(initialState)
    }

    fun SubscribeTo(events: Observable<LoginUIEvent>) : Observable<LoginUIModel> {

        val results = events.map { ev -> LoginAction(ev.username) }
                .publish { shared -> shared.compose(loginTransformer) }
        val uiModels = results.map {
            LoginUIModel(InProgress = it.InProgress, Message = it.Message, Success = it.Success)
        }

        uiModels.subscribe(loginUISubject)
        return loginUISubject
    }
}