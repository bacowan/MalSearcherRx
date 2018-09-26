package moe.cowan.brendan.malsearcherrx.View

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.login_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIModel
import javax.inject.Inject
import android.view.inputmethod.InputMethodManager
import moe.cowan.brendan.malsearcherrx.ViewModel.LoginViewModel


class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
        val vm = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]

        val uiEvents = setupUiEvents()
        val uiModels = vm.SubscribeTo(uiEvents)

        disposables.add(uiModels.subscribe { model -> updateUI(model) } )
    }

    private fun setupUiEvents() : Observable<LoginUIEvent> {
        val imeDoneEvent = RxTextView.editorActionEvents(username_edit_text)
                .filter { event -> event.actionId() == EditorInfo.IME_ACTION_DONE }
                .doOnNext { _ -> hideKeyboard() }
                .map { _ -> LoginUIEvent(username_edit_text.text.toString()) }

        val loginButtonClickEvent = RxView.clicks(submit_username_button)
                .map { _ -> LoginUIEvent(username_edit_text.text.toString()) }

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
        model.SuccessfulUsername?.let {
            val act = activity
            if (act is OnLoginListener) {
                act.OnLogin(it)
            }
        }
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    interface OnLoginListener {
        fun OnLogin(username: String)
    }
}