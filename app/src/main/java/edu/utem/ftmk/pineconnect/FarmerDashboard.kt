package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FarmerDashboard : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_dashboard)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val greetingText = findViewById<TextView>(R.id.farmerGreeting)

        // 🔹 Get current user UID
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    val name = doc.getString("name")
                    if (!name.isNullOrEmpty()) {
                        greetingText.text = "Hello, $name"
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load user info", Toast.LENGTH_SHORT).show()
                }
        }

        // Profile icon → FarmerProfileActivity
        val profileIcon = findViewById<ImageView>(R.id.iconProfile)
        profileIcon.setOnClickListener {
            val intent = Intent(this, FarmerProfileActivity::class.java)
            startActivity(intent)
        }

        // Settings icon → FarmerSettingsActivity
        val settingsIcon = findViewById<ImageView>(R.id.iconSettings)
        settingsIcon.setOnClickListener {
            val intent = Intent(this, FarmerSettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
