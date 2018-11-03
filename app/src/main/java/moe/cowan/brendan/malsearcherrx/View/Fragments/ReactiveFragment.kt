package moe.cowan.brendan.malsearcherrx.View.Fragments

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.MissingViewModelException
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.SubscribableViewModel
import java.lang.ClassCastException
import javax.inject.Inject

abstract class ReactiveFragment<TEvent, TModel, TPost> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModelClass: Class<SubscribableViewModel<TEvent, TModel, TPost>>

    private var disposables = CompositeDisposable()

    protected abstract val layout: Int

    private val externalEvents: ReplaySubject<TEvent> = ReplaySubject.create()

    @Override
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            try {
                viewModelClass = it.getSerializable("VIEW_MODEL_CLASS") as Class<SubscribableViewModel<TEvent, TModel, TPost>>
            }
            catch (e: ClassCastException) {
                throw MissingViewModelException()
            }
        } ?: throw MissingViewModelException()
        return inflater.inflate(layout, container, false)
    }

    @Override
    override fun onStart() {
        super.onStart()
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }
        setupStreams()
    }

    @Override
    override fun onStop() {
        super.onStop()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    @Override
    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    private fun setupStreams() {
        val vm = ViewModelProviders.of(this, viewModelFactory)[viewModelClass]

        val uiEvents = setupUiEvents().mergeWith(externalEvents)
        val (uiModels, posts) = vm.subscribeTo(uiEvents)

        disposables.add(uiModels.subscribe { updateUIModel(it) })

        disposables.add(posts.subscribe { updateUIPost(it) })
    }

    protected abstract fun getErrorPost(error: Throwable): TPost

    protected abstract fun setupUiEvents() : Observable<TEvent>

    protected open fun updateUIModel(model: TModel) {}

    protected open fun updateUIPost(post: TPost) {}

    fun sendEvent(event: TEvent) {
        externalEvents.onNext(event)
    }
}