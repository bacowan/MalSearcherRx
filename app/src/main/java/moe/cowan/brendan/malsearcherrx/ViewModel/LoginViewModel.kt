package moe.cowan.brendan.malsearcherrx.ViewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.LoginTransformer
import moe.cowan.brendan.malsearcherrx.Reactive.Actions.LoginAction
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIPost
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchUIPost
import javax.inject.Inject

class LoginViewModel @Inject constructor(): SubscribableViewModel<LoginUIEvent, LoginUIModel, LoginUIPost>(
        LoginUIModel(InProgress = false, SuccessfulUsername = null, Message = "")
) {
    @Inject
    lateinit var loginTransformer: LoginTransformer

    override fun subscribe(events: Observable<LoginUIEvent>) : Pair<Observable<LoginUIModel>?, Observable<LoginUIPost>?> {
        val results = events.map { ev -> LoginAction(ev.username) }
                .publish { shared -> shared.compose(loginTransformer) }
        val uiModels = results.map {
            LoginUIModel(InProgress = it.InProgress, Message = it.Message, SuccessfulUsername = it.SuccessfulUsername)
        }

        return Pair(uiModels, null)
    }
}