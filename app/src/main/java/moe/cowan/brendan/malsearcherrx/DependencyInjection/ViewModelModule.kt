package moe.cowan.brendan.malsearcherrx.DependencyInjection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.AnimeSearchViewModel
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.CharacterSearchViewModel
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.LoginViewModel
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.MainSearchViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

// see: https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun ViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun LoginViewModel(vm: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainSearchViewModel::class)
    abstract fun SearchViewModel(vm: MainSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnimeSearchViewModel::class)
    abstract fun AnimeSearchViewModel(vm: AnimeSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterSearchViewModel::class)
    abstract fun CharacterSearchViewModel(vm: CharacterSearchViewModel): ViewModel
}