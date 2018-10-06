package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.View.ReactiveFragmentFactory

@Module
class FragmentModule {
    @Provides
    fun provideFragmentFactory(): ReactiveFragmentFactory = ReactiveFragmentFactory()
}