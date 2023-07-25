package com.furkanbostan.moneymanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.furkanbostan.moneymanagement.databinding.ActivityMainBinding
import com.furkanbostan.moneymanagement.util.hideKeyBoard

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val bottomNav=binding.bottomNavMainActivity
        NavigationUI.setupWithNavController(bottomNav,navHostFragment.navController)


    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyBoard()
        }
        return super.dispatchTouchEvent(ev)
    }
}