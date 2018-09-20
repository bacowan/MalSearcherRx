package moe.cowan.brendan.malsearcherrx.View

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.login_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.LoginTransformer
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIModel
import javax.inject.Inject
import android.view.inputmethod.InputMethodManager
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginAction
import io.reactivex.internal.util.NotificationLite.disposable
import io.reactivex.internal.disposables.DisposableHelper.dispose




class LoginFragment : Fragment() {

    @Inject
    lateinit var loginTransformer: LoginTransformer

    private val disposables = CompositeDisposable()

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    @Override
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Override
    override fun onStart() {
        super.onStart()
        setupStreams()
    }

    @Override
    override fun onStop() {
        super.onStop()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun setupStreams() {
        val initialState = LoginUIModel(InProgress = false, Success = false, Message = "")
        val events = setupUiEvents()
        val eventTransformer = events.publish { shared -> shared.compose(loginTransformer) }
        val uiModels = eventTransformer.scan(initialState) { state, result ->
                LoginUIModel(InProgress = result.InProgress, Message = result.Message, Success = result.Success)
        }
        disposables.add(uiModels.subscribe { model -> updateUI(model) } )
    }

    private fun setupUiEvents() : Observable<LoginAction> {
        val imeDoneEvent = RxTextView.editorActionEvents(username_edit_text)
                .filter { event -> event.actionId() == EditorInfo.IME_ACTION_DONE }
                .doOnNext { _ -> hideKeyboard() }
                .map { _ -> LoginAction(username_edit_text.text.toString()) }

        val loginButtonClickEvent = RxView.clicks(submit_username_button)
                .map { _ -> LoginAction(username_edit_text.text.toString()) }

        return imeDoneEvent.mergeWith(loginButtonClickEvent)
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun updateUI(model: LoginUIModel) {
        progress_bar_layout.visibility = if (model.InProgress) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
        if (!model.Message.isEmpty()) {
            Toast.makeText(context, model.Message, Toast.LENGTH_SHORT).show()
        }
        if (model.Success) {
        }
        else {
        }
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}