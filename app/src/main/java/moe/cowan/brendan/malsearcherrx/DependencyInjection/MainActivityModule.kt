package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.Model.AlwaysValidLoginService
import moe.cowan.brendan.malsearcherrx.Model.LoginService
import moe.cowan.brendan.malsearcherrx.Reactive.Transformers.LoginTransformer

@Module
class MainActivityModule {
    @Provides
    fun providesLoginTransformer(): LoginTransformer = LoginTransformer()

    @Provides
    fun providesLoginService(): LoginService = AlwaysValidLoginService()
}