package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.View.FragmentFactory

@Module
class FragmentModule {
    @Provides
    fun provideFragmentFactory(): FragmentFactory = FragmentFactory()
}