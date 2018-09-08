package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.Model.AlwaysValidLoginService
import moe.cowan.brendan.malsearcherrx.Model.LoginService

@Module
class BackEndModule {
    @Provides
    fun LoginService() : LoginService = AlwaysValidLoginService()
}