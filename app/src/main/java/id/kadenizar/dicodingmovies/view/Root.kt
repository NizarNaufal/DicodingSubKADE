package id.kadenizar.dicodingmovies.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.greenfrvr.rubberloader.RubberLoaderView
import id.kadenizar.dicodingmovies.R
import id.kadenizar.dicodingmovies.services.IView
import id.kadenizar.dicodingmovies.services.ViewNetworkState
import id.kadenizar.dicodingmovies.services.base.BaseActivity
import id.kadenizar.dicodingmovies.support.showActivity
import java.util.*

class Root : BaseActivity(),ViewNetworkState,IView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //For splash delay time
        (findViewById<View>(R.id.loader1) as RubberLoaderView).startLoading()
        val myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                // If you want to modify a view in your Activity
                runOnUiThread {
                   initView()
                }
            }
        }, 500)

    }
    override fun initView() {
        showActivity(HomeActivity::class.java)
    }
}