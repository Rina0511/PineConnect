package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FarmerSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_settings) // your XML file

        // Back button → finish activity
        val backIcon = findViewById<ImageView>(R.id.iconBack)
        backIcon.setOnClickListener {
            finish() // closes settings and returns to dashboard
        }

        // Settings icon → stays in settings (optional toast)
        val settingsIcon = findViewById<ImageView>(R.id.iconSettings)
        settingsIcon.setOnClickListener {
            Toast.makeText(this, "You are already in Settings", Toast.LENGTH_SHORT).show()
        }

        // Language option
        val rateAppOption = findViewById<TextView>(R.id.optionRateApp)
        rateAppOption.setOnClickListener {
            Toast.makeText(this, "Rate App clicked", Toast.LENGTH_SHORT).show()
        }

        val shareAppOption = findViewById<TextView>(R.id.optionShareApp)
        shareAppOption.setOnClickListener {
            Toast.makeText(this, "Share App clicked", Toast.LENGTH_SHORT).show()
        }

        val privacyOption = findViewById<TextView>(R.id.optionPrivacyPolicy)
        privacyOption.setOnClickListener {
            Toast.makeText(this, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
        }

        val termsOption = findViewById<TextView>(R.id.optionTerms)
        termsOption.setOnClickListener {
            Toast.makeText(this, "Terms & Conditions clicked", Toast.LENGTH_SHORT).show()
        }

        val feedbackOption = findViewById<TextView>(R.id.optionFeedback)
        feedbackOption.setOnClickListener {
            Toast.makeText(this, "Feedback clicked", Toast.LENGTH_SHORT).show()
        }

        val logoutOption = findViewById<TextView>(R.id.optionLogout)
        logoutOption.setOnClickListener {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
}
