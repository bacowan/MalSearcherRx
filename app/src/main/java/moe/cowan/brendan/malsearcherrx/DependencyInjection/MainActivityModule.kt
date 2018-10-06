package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import moe.cowan.brendan.malsearcherrx.MainActivity
import moe.cowan.brendan.malsearcherrx.Model.AlwaysValidLoginService
import moe.cowan.brendan.malsearcherrx.View.LoginFragment
import moe.cowan.brendan.malsearcherrx.View.ReactiveFragmentFactory
import moe.cowan.brendan.malsearcherrx.View.SearchFragment

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeActivityInjector() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginFragmentInjector() : LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragmentInjector() : SearchFragment
}