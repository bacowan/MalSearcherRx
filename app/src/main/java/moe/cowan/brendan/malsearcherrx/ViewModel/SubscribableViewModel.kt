package moe.cowan.brendan.malsearcherrx.ViewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class SubscribableViewModel<TEvent, TModel, TPost>(initialState: TModel) : ViewModel() {

    private val modelSubject: BehaviorSubject<TModel> = BehaviorSubject.create()
    private val postSubject: PublishSubject<TPost> = PublishSubject.create()

    init {
        modelSubject.onNext(initialState)
    }

    fun SubscribeTo(events: Observable<TEvent>) : Pair<Observable<TModel>, Observable<TPost>> {
        val (model, post) = subscribe(events)
        model?.subscribe(modelSubject)
        post?.subscribe(postSubject)
        return Pair(modelSubject, postSubject)
    }

    protected abstract fun subscribe(events: Observable<TEvent>) : Pair<Observable<TModel>?, Observable<TPost>?>
}