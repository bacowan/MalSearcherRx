package moe.cowan.brendan.malsearcherrx.ViewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.LoginTransformer
import moe.cowan.brendan.malsearcherrx.Reactive.Actions.LoginAction
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var loginTransformer: LoginTransformer

    private val loginUISubject: BehaviorSubject<LoginUIModel> = BehaviorSubject.create()

    init {
        val initialState = LoginUIModel(InProgress = false, SuccessfulUsername = null, Message = "")
        loginUISubject.onNext(initialState)
    }

    fun SubscribeTo(events: Observable<LoginUIEvent>) : Observable<LoginUIModel> {
        val results = events.map { ev -> LoginAction(ev.username) }
                .publish { shared -> shared.compose(loginTransformer) }
        val uiModels = results.map {
            LoginUIModel(InProgress = it.InProgress, Message = it.Message, SuccessfulUsername = it.SuccessfulUsername)
        }

        uiModels.subscribe(loginUISubject)
        return loginUISubject
    }
}