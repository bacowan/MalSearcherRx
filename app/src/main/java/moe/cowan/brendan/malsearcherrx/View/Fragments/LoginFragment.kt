package moe.cowan.brendan.malsearcherrx.View.Fragments

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.login_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Login.LoginUIModel
import android.view.inputmethod.InputMethodManager
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.LoginErrorPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.LoginUIPost


class LoginFragment : ReactiveFragment<LoginUIEvent, LoginUIModel, LoginUIPost>() {

    override val layout get() = R.layout.login_fragment

    @Override
    override fun setupUiEvents() : Observable<LoginUIEvent> {
        val imeDoneEvent = RxTextView.editorActionEvents(username_edit_text)
                .filter { event -> event.actionId() == EditorInfo.IME_ACTION_DONE }
                .doOnNext { hideKeyboard() }
                .map { LoginUIEvent(username_edit_text.text.toString()) }

        val loginButtonClickEvent = RxView.clicks(submit_username_button)
                .map { LoginUIEvent(username_edit_text.text.toString()) }

        return imeDoneEvent.mergeWith(loginButtonClickEvent)
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    @Override
    override fun updateUIModel(model: LoginUIModel) {
        progress_bar_layout.visibility = when (model.InProgress) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        if (!model.Message.isEmpty()) {
            Toast.makeText(context, model.Message, Toast.LENGTH_SHORT).show()
        }
        model.SuccessfulUsername?.let {
            val act = activity
            if (act is OnLoginListener) {
                act.OnLogin(it)
            }
        }
    }

    @Override
    override fun getErrorPost(error: Throwable): LoginUIPost = LoginErrorPost(error.message ?: "oops")

    interface OnLoginListener {
        fun OnLogin(username: String)
    }
}