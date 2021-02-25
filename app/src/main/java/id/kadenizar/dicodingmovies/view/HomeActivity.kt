package id.kadenizar.dicodingmovies.view

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.kadenizar.dicodingmovies.R
import id.kadenizar.dicodingmovies.services.IView
import id.kadenizar.dicodingmovies.services.ViewNetworkState
import id.kadenizar.dicodingmovies.services.base.BaseActivity
import id.kadenizar.dicodingmovies.support.showToast

class HomeActivity : BaseActivity(), ViewNetworkState, IView {

    private var lastPosition = 0
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    override fun initView() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie,
                R.id.navigation_series,
                R.id.navigation_search,
                R.id.navigation_favorite
            )
        )

        //Prevent re-create same fragment
        navView.setOnNavigationItemReselectedListener {
            //Do nothing
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (lastPosition == 0) {

            if (doubleBackToExitPressedOnce) {
                finishAffinity()
            } else {
                this.doubleBackToExitPressedOnce = true
                showToast(getString(R.string.message_double_klik_to_close))
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }

        }
    }


}