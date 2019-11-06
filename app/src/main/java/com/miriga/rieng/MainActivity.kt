package com.miriga.rieng

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        bottomNavigationView = findViewById(R.id.nav_view)
        navController = Navigation.findNavController(this, R.id.main_nav_fragment)
        setupActionBarWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment ->{
                    hideBottomNav()
                }
                R.id.quizFragment -> {
                    hideBottomNav()
                }
//                R.id.newDetailFragment -> {
//                    changeToolBarColor("news")
//                    hideBottomNav()
//                }
//                R.id.couponDetailFragment ->{
//                    changeToolBarColor("offers")
//                    hideBottomNav()
//                }
//                R.id.editProfileFragment -> hideBottomNav()
//                R.id.newsFragment -> {
//                    changeToolBarColor("news")
//                    showBottomNav()
//                }
//                R.id.offersFragment -> {
//                    changeToolBarColor("offers")
//                    showBottomNav()
//                }
//                R.id.eventFragment -> {
//                    changeToolBarColor("events")
//                    showBottomNav()
//                }
                else -> {
                    showBottomNav()
//                    changeToolBarColor("else")
                }
            }
        }

    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_fragment).navigateUp()

    private fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    fun changeToolBarColor(frag: String) {
        val color = when (frag) {
            "news" -> "#A4E3f4"
            "offers" -> "#ffc408"
            "events" -> "#ff5a5a"
            else -> "#084261"
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))

        val colorInts = getColors(frag)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            window.statusBarColor = ContextCompat.getColor(this, colorInts)
        }
    }

    private fun getColors(colorstr:String):Int{
        return when (colorstr) {
            "news" -> R.color.green_5
            "offers" -> R.color.yellow
            "events" -> R.color.red
            else -> R.color.colorPrimary
        }
    }
}
