package moe.cowan.brendan.malsearcherrx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            val fragment = SearchFragment()
            supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragment).commit()
        }

    }
}
