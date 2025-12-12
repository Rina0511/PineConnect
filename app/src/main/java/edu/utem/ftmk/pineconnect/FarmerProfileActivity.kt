package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FarmerProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val profileName = findViewById<TextView>(R.id.profileName)
        val profileEmail = findViewById<TextView>(R.id.profileEmail)

        // 🔹 Fetch current farmer info
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    val name = doc.getString("name")
                    val email = doc.getString("email")

                    if (!name.isNullOrEmpty()) {
                        profileName.text = name
                    }
                    if (!email.isNullOrEmpty()) {
                        profileEmail.text = email
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load farmer profile", Toast.LENGTH_SHORT).show()
                }
        }

        // Back button → finish activity
        val backIcon = findViewById<ImageView>(R.id.iconBack)
        backIcon.setOnClickListener { finish() }

        // Settings icon → optional toast
        val settingsIcon = findViewById<ImageView>(R.id.iconSettings)
        settingsIcon.setOnClickListener {
            Toast.makeText(this, "Settings icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Edit Profile button
        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
<<<<<<< HEAD
            // Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show() // Remove this line if desired

            // 🚀 ACTION: Launch the Activity that hosts the EditProfileFragment
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
=======
            Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show()
            // Example: navigate to EditProfileActivity if you have one
            // startActivity(Intent(this, EditProfileActivity::class.java))
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
        }

        // Language option
        val languageOption = findViewById<TextView>(R.id.languageOption)
        languageOption.setOnClickListener {
            Toast.makeText(this, "Language option clicked", Toast.LENGTH_SHORT).show()
        }

        // Location option
        val locationOption = findViewById<TextView>(R.id.locationOption)
        locationOption.setOnClickListener {
            Toast.makeText(this, "Location option clicked", Toast.LENGTH_SHORT).show()
        }

        // Clear Cache option
        val clearCacheOption = findViewById<TextView>(R.id.clearCacheOption)
        clearCacheOption.setOnClickListener {
            Toast.makeText(this, "Cache cleared", Toast.LENGTH_SHORT).show()
        }

        // Clear History option
        val clearHistoryOption = findViewById<TextView>(R.id.clearHistoryOption)
        clearHistoryOption.setOnClickListener {
            Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show()
        }

        // Logout option
        val logoutOption = findViewById<TextView>(R.id.logoutOption)
        logoutOption.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
