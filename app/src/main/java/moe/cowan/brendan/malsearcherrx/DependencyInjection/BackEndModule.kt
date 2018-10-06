package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.Model.Services.AlwaysValidLoginService
import moe.cowan.brendan.malsearcherrx.Model.Services.AnimeSearcher
import moe.cowan.brendan.malsearcherrx.Model.Services.LoginService
import moe.cowan.brendan.malsearcherrx.Model.Services.OnlyPokemonAnimeSearcher

@Module
class BackEndModule {
    @Provides
    fun LoginService() : LoginService = AlwaysValidLoginService()

    @Provides
    fun AnimeSearcher() : AnimeSearcher = OnlyPokemonAnimeSearcher()
}