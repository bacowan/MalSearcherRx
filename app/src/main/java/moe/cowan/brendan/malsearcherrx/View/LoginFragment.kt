package moe.cowan.brendan.malsearcherrx.View

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_fragment.*
import moe.cowan.brendan.malsearcherrx.Model.LoginService
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.LoginUIModel
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var loginService: LoginService

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

    private fun setupStreams() {
        val events = RxView.clicks(submit_username_button)
                .map { _ -> LoginUIEvent(username_edit_text.text.toString()) }

        val models = events
                .flatMap { event -> loginService.VerifyLogin(event.username)
                        .map { response -> LoginUIModel(Success = response, InProgress = false, Message = "success") }
                        .onErrorReturn { _ -> LoginUIModel(Success = false, InProgress = false, Message = "oops") }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .startWith(LoginUIModel(Success = false, InProgress = true)) }

        disposables.add(models.subscribe { model ->
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
        })
    }
}