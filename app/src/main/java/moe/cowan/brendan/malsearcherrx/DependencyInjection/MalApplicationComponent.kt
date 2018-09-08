package moe.cowan.brendan.malsearcherrx.DependencyInjection

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import moe.cowan.brendan.malsearcherrx.MalSearcherApplication
import javax.inject.Singleton

@Component(modules = [MainActivityModule::class, BackEndModule::class, AndroidSupportInjectionModule::class])
@Singleton
interface MalApplicationComponent : AndroidInjector<MalSearcherApplication>