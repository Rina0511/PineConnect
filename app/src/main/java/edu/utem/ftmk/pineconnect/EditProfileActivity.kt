package edu.utem.ftmk.pineconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 🚨 IMPORTANT: You need a simple layout file (e.g., activity_edit_profile)
        // that contains a FrameLayout to host the fragment.
        setContentView(R.layout.activity_edit_profile)

        // Load the fragment into the FrameLayout
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EditProfileFragment()) // Use the FrameLayout ID
                .commit()
        }
    }

    // Optional: Override to allow fragment to handle back press, or just finish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}