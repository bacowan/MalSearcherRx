package moe.cowan.brendan.malsearcherrx.View

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
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIEvents.Login.LoginUIEvent
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIModel
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Login.LoginUIPost
import moe.cowan.brendan.malsearcherrx.ViewModel.MissingViewModelException
import moe.cowan.brendan.malsearcherrx.ViewModel.SubscribableViewModel
import java.lang.ClassCastException
import javax.inject.Inject

abstract class ReactiveFragment<TEvent, TModel, TPost> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModelClass: Class<SubscribableViewModel<TEvent, TModel, TPost>>

    private var disposables = CompositeDisposable()

    protected abstract val layout: Int

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

        val uiEvents = setupUiEvents()
        val (uiModels, posts) = vm.SubscribeTo(uiEvents)

        disposables.add(uiModels.subscribe { model -> updateUIModel(model) } )
        disposables.add(posts.subscribe { post -> updateUIPost(post) } )
    }

    protected abstract fun setupUiEvents() : Observable<TEvent>

    protected open fun updateUIModel(model: TModel) {}

    protected open fun updateUIPost(post: TPost) {}
}