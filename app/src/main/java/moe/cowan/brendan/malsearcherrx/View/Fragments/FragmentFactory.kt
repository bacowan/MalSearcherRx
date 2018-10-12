package moe.cowan.brendan.malsearcherrx.View.Fragments

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.SubscribableViewModel
import moe.cowan.brendan.malsearcherrx.View.Dialogs.ReactiveDialog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FragmentFactory @Inject constructor(val viewModelFactory: ViewModelProvider.Factory) {

    inline fun <reified TFragment: ReactiveFragment<*, *, *>, reified TViewModel: SubscribableViewModel<*, *, *>> createFragment(
            initializer: (vm: TViewModel) -> Unit = {}
    ): TFragment {
        val fragment = TFragment::class.java.newInstance()
        val args = Bundle()
        args.putSerializable("VIEW_MODEL_CLASS", TViewModel::class.java)
        fragment.arguments = args
        initializer(ViewModelProviders.of(fragment, viewModelFactory)[TViewModel::class.java])
        return fragment
    }

    inline fun <reified TFragment: ReactiveDialog<*, *, *>, reified TViewModel: SubscribableViewModel<*, *, *>> createDialogFragment(
            initializer: (vm: TViewModel) -> Unit = {}
    ): TFragment {
        val fragment = TFragment::class.java.newInstance()
        val args = Bundle()
        args.putSerializable("VIEW_MODEL_CLASS", TViewModel::class.java)
        fragment.arguments = args
        initializer(ViewModelProviders.of(fragment, viewModelFactory)[TViewModel::class.java])
        return fragment
    }
}