package com.exprnc.winditechnicaltask.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.FixedNavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            val navHost = FixedNavHostFragment.create(R.navigation.auth_nav_graph)
            replace(R.id.frameLayout, navHost)
            addToBackStack(null)
        }
    }
}