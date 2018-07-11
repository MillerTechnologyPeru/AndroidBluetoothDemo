package com.jmarkstar.saa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ActionBarProvider {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        router = Conductor.attachRouter(this, controller_container, savedInstanceState)

        if(!router!!.hasRootController()){

        }
    }

    override fun onBackPressed() {
        if(!router!!.handleBack()){
            super.onBackPressed()
        }
    }
}
