package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_dashboard)

        val navBottom = findViewById<BottomNavigationView>(R.id.navBottom)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
        val iconSettings = findViewById<ImageView>(R.id.iconSettings)
        iconSettings.setOnClickListener {
            loadFragment(SettingFragment())
        }


        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navHome -> { loadFragment(HomeFragment()); true }
                R.id.navCart -> { loadFragment(CartFragment()); true }
                R.id.navNotifications -> { loadFragment(NotificationFragment()); true }
                R.id.navProfile -> { loadFragment(ProfileFragment()); true }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
