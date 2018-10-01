package moe.cowan.brendan.malsearcherrx.ViewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable

abstract class SubscribableViewModel<TEvent, TModel, TPost> : ViewModel() {
    abstract fun SubscribeTo(events: Observable<TEvent>) : Pair<Observable<TModel>, Observable<TPost>>
}