package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import moe.cowan.brendan.malsearcherrx.Model.AlwaysValidLoginService
import moe.cowan.brendan.malsearcherrx.Model.LoginService
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.LoginTransformer
import moe.cowan.brendan.malsearcherrx.View.LoginFragment

@Module
class MainActivityModule {
    @Provides
    fun providesLoginTransformer(): LoginTransformer = LoginTransformer()

    @Provides
    fun providesLoginService(): LoginService = AlwaysValidLoginService()
}