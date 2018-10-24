package moe.cowan.brendan.malsearcherrx.DependencyInjection

import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import moe.cowan.brendan.malsearcherrx.View.Dialogs.*
import moe.cowan.brendan.malsearcherrx.View.MainActivity
import moe.cowan.brendan.malsearcherrx.View.Fragments.LoginFragment
import moe.cowan.brendan.malsearcherrx.View.Fragments.MainSearchFragment

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeActivityInjector() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginFragmentInjector() : LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragmentInjector() : MainSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeImageTextSearchDialog() : ImageTextSearchDialog

    @ContributesAndroidInjector
    abstract fun contributeTextSearchDialog() : TextSearchDialog
}