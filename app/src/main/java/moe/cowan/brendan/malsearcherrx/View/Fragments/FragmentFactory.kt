package moe.cowan.brendan.malsearcherrx.View.Fragments

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment

class FragmentFactory {
    inline fun <reified TFragment: Fragment, reified TViewModel: ViewModel> createFragment(): Fragment {
        val fragment = TFragment::class.java.newInstance()
        val args = Bundle()
        args.putSerializable("VIEW_MODEL_CLASS", TViewModel::class.java)
        fragment.arguments = args
        return fragment
    }

    inline fun <reified TFragment: DialogFragment, reified TViewModel: ViewModel> createDialogFragment(): DialogFragment {
        val fragment = TFragment::class.java.newInstance()
        val args = Bundle()
        args.putSerializable("VIEW_MODEL_CLASS", TViewModel::class.java)
        fragment.arguments = args
        return fragment
    }
}