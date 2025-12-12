package edu.utem.ftmk.pineconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // =========================================================
        // ✅ CRITICAL FIX: Tell the Activity which XML to display!
        // =========================================================
        setContentView(R.layout.activity_add_user)
    }
}