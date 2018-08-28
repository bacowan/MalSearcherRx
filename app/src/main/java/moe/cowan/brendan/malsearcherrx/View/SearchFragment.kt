package moe.cowan.brendan.malsearcherrx.View

import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.SearchUIModel

class SearchFragment : Fragment() {
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
                .map { _ -> SearchUIModel(showLoginDialog = true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .startWith(initialSearchUIModel())

        disposables.add(models.subscribe { model ->
            if (model.showLoginDialog) {
                //LoginDialogFragment().show(fragmentManager, "LoginDialogFragment")
            }
        })
    }

    private fun createPreferencesObservable(): Observable<SearchUIEvent> {
        return Observable.create { emitter ->
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key != null && sharedPreferences?.contains(key) == true) {
                    emitter.onNext(SearchUIEvent(key, sharedPreferences.getString(key, "")))
                }
            }
            PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext).registerOnSharedPreferenceChangeListener(listener)
            emitter.setCancellable {
                PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext).unregisterOnSharedPreferenceChangeListener(listener)
            }
        }
    }

    private fun initialSearchUIModel() : SearchUIModel {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        val username = preferences.getString("username", "")
        return when (username) {
            "" -> SearchUIModel(showLoginDialog = true)
            else -> SearchUIModel(showLoginDialog = false)
        }
    }
}