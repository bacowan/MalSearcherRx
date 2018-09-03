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
        val events = setupUiEvents()
        disposables.add(events.compose(loginTransformer).subscribe { model -> updateUI(model) } )
    }

    private fun setupUiEvents() : Observable<LoginUIEvent> {
        val imeDoneEvent = RxTextView.editorActionEvents(username_edit_text)
                .filter { event -> event.actionId() == EditorInfo.IME_ACTION_DONE }
                .map { _ -> LoginUIEvent(username_edit_text.text.toString()) }

        val loginButtonClickEvent = RxView.clicks(submit_username_button)
                .map { _ -> LoginUIEvent(username_edit_text.text.toString()) }

        return imeDoneEvent.mergeWith(loginButtonClickEvent)
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
}