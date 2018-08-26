package moe.cowan.brendan.malsearcherrx

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stylingandroid.prefekt.Subscriber
import com.stylingandroid.prefekt.prefekt
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.Reactive.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.SearchUIModel

class SearchFragment : Fragment() {
    private val usernameSetting = prefekt(resources.getString(R.string.username_setting), "") {}
    private val disposables = CompositeDisposable()

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    @Override
    override fun onStart() {
        super.onStart()
        setupStreams()
    }

    private fun setupStreams() {
        val events = createPreferencesObservable()

        val models = events
                .map { response -> SearchUIModel(showLoginDialog = true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

        disposables.add(models.subscribe { model ->
            if (model.showLoginDialog) {
                Toast.makeText(activity?.applicationContext, "Hello world", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createPreferencesObservable(): Observable<SearchUIEvent> {
        return Observable.create { emitter ->
            val stringSubscriber =  object: Subscriber<String> {
                override fun onChanged(newValue: String) {
                    emitter.onNext(SearchUIEvent(R.string.username_setting, newValue))
                }
            }
            usernameSetting.subscribe(stringSubscriber)
            emitter.setCancellable {
                usernameSetting.unsubscribe(stringSubscriber)
            }
        }
    }
}