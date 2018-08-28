package moe.cowan.brendan.malsearcherrx

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import moe.cowan.brendan.malsearcherrx.DependencyInjection.MainActivityModule
import moe.cowan.brendan.malsearcherrx.View.LoginFragment
import moe.cowan.brendan.malsearcherrx.View.SearchFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasFragmentInjector {

    override fun fragmentInjector(): AndroidInjector<Fragment> = injector

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val username = preferences.getString("username", "")
            val fragment = when (username) {
                "" -> LoginFragment()
                else -> SearchFragment()
            }
            supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragment).commit()
        }
    }
}
