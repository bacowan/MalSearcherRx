package moe.cowan.brendan.malsearcherrx.Presenter.ViewModels

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Presenter.ActionToResultTransformers.LoginTransformer
import moe.cowan.brendan.malsearcherrx.Presenter.Actions.LoginAction
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Login.LoginUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.LoginUIPost
import javax.inject.Inject

class LoginViewModel @Inject constructor(): SubscribableViewModel<LoginUIEvent, LoginUIModel, LoginUIPost>(

) {
    @Inject
    lateinit var loginTransformer: LoginTransformer

    override fun subscribe(events: Observable<LoginUIEvent>) : Pair<Observable<LoginUIModel>?, Observable<LoginUIPost>?> {
        val results = events.map { ev -> LoginAction(ev.username) }
                .publish { shared -> shared.compose(loginTransformer) }

        val uiModels = results.scan(LoginUIModel(InProgress = false, SuccessfulUsername = null, Message = "")) { previous, current ->
            LoginUIModel(InProgress = current.InProgress, Message = current.Message, SuccessfulUsername = current.SuccessfulUsername)
        }

        return Pair(uiModels, null)
    }
}