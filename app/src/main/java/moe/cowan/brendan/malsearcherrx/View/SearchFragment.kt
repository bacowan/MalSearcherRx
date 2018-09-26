package moe.cowan.brendan.malsearcherrx.View

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moe.cowan.brendan.malsearcherrx.R

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