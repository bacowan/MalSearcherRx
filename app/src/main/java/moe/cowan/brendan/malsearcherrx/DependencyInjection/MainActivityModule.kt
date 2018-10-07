package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import moe.cowan.brendan.malsearcherrx.View.MainActivity
import moe.cowan.brendan.malsearcherrx.View.Fragments.LoginFragment
import moe.cowan.brendan.malsearcherrx.View.Dialogs.SearchDialog
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
    abstract fun contributeSearchDialog() : SearchDialog
}