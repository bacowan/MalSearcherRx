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
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.SearchUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIData.SearchUIModel

class SearchFragment : Fragment() {

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    @Override
    override fun onStart() {
        super.onStart()
    }
}