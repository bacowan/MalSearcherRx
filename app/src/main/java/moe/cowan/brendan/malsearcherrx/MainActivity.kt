package moe.cowan.brendan.malsearcherrx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import moe.cowan.brendan.malsearcherrx.View.LoginFragment
import moe.cowan.brendan.malsearcherrx.View.ReactiveFragmentFactory
import moe.cowan.brendan.malsearcherrx.View.SearchFragment
import moe.cowan.brendan.malsearcherrx.ViewModel.LoginViewModel
import moe.cowan.brendan.malsearcherrx.ViewModel.SearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, LoginFragment.OnLoginListener {

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var fragmentFactory: ReactiveFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val username = preferences.getString("username", "")
            val fragment = when (username) {
                "" -> fragmentFactory.createFragment<LoginFragment, LoginViewModel>()
                else -> fragmentFactory.createFragment<SearchFragment, SearchViewModel>()
            }
            supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragment).commit()
        }
    }

    override fun OnLogin(username: String) {
        saveUsername(username)
        val fragment = fragmentFactory.createFragment<SearchFragment, SearchViewModel>()
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
    }

    private fun saveUsername(username: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
        preferences.putString("username", username)
        preferences.apply()
    }
}
