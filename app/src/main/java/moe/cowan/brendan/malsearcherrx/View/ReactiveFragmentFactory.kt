package moe.cowan.brendan.malsearcherrx.View

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.Fragment
import moe.cowan.brendan.malsearcherrx.ViewModel.LoginViewModel

class ReactiveFragmentFactory {
    inline fun <reified TFragment: Fragment, reified TViewModel: ViewModel> createFragment(): Fragment {
        val fragment = TFragment::class.java.newInstance()
        val args = Bundle()
        args.putSerializable("VIEW_MODEL_CLASS", TViewModel::class.java)
        fragment.arguments = args
        return fragment
    }
}