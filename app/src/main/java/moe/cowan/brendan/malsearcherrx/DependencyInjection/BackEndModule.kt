package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.Model.Services.*

@Module
class BackEndModule {
    @Provides
    fun LoginService() : LoginService = AlwaysValidLoginService()

    @Provides
    fun AnimeSearcher() : AnimeSearcher = OnlyPokemonAnimeSearcher()

    @Provides
    fun CharacterSearcher() : CharacterSearcher = OnlyPikachuCharacterSearcher()
}