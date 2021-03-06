package moe.cowan.brendan.malsearcherrx

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import moe.cowan.brendan.malsearcherrx.DependencyInjection.BackEndModule
import moe.cowan.brendan.malsearcherrx.DependencyInjection.DaggerMalApplicationComponent
import moe.cowan.brendan.malsearcherrx.DependencyInjection.MainActivityModule
import javax.inject.Inject

class MalSearcherApplication : Application(), HasActivityInjector {

    override fun activityInjector(): AndroidInjector<Activity> = injector

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerMalApplicationComponent.builder()
                .backEndModule(BackEndModule())
                .build().inject(this)
    }
}