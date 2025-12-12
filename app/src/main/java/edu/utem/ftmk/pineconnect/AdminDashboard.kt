package edu.utem.ftmk.pineconnect

<<<<<<< HEAD
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button // <-- IMPORTANT: Needed for Button views
=======
import android.content.Intent
import android.os.Bundle
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

<<<<<<< HEAD
// ======================================================
// ✅ CRITICAL FIX: Missing Activity Imports
// Add imports for the Activities the dashboard will launch.
// You MUST create these files if they don't exist!
// ======================================================
import edu.utem.ftmk.pineconnect.MainActivity
import edu.utem.ftmk.pineconnect.AddUserActivity
import edu.utem.ftmk.pineconnect.EditUserActivity
import edu.utem.ftmk.pineconnect.SuspendUserActivity
import edu.utem.ftmk.pineconnect.NewsManagementActivity
import edu.utem.ftmk.pineconnect.ProductMonitoringActivity
import edu.utem.ftmk.pineconnect.ComplaintHandlingActivity
import edu.utem.ftmk.pineconnect.SystemSettingsActivity
import edu.utem.ftmk.pineconnect.AdminAnalyticsActivity // Changed from a generic name


=======
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
class AdminDashboard : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

<<<<<<< HEAD
    @SuppressLint("SetTextI18n", "MissingInflatedId")
=======
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_dashboard)

        auth = FirebaseAuth.getInstance()

        val greetingText = findViewById<TextView>(R.id.adminGreeting)
<<<<<<< HEAD

        // Fix for Null Safety for Admin Name Retrieval
        val adminName = intent.getStringExtra("adminName") ?: "Admin"
        greetingText.text = "Hello, $adminName"

        // ======================================================
        // ✅ IMPLEMENTING ALL BUTTON CLICK ACTIONS
        // These IDs are matched exactly to your activity_admin_dashboard.xml
        // ======================================================

        // --- User Management Buttons ---
        findViewById<Button>(R.id.btnAddUser)?.setOnClickListener {
            startActivity(Intent(this, AddUserActivity::class.java))
        }

        findViewById<Button>(R.id.btnEditUser)?.setOnClickListener {
            startActivity(Intent(this, EditUserActivity::class.java))
        }

        findViewById<Button>(R.id.btnSuspendUser)?.setOnClickListener {
            startActivity(Intent(this, SuspendUserActivity::class.java))
        }

        // --- Other Modules Buttons ---
        findViewById<Button>(R.id.btnNews)?.setOnClickListener {
            startActivity(Intent(this, NewsManagementActivity::class.java))
        }

        findViewById<Button>(R.id.btnProduct)?.setOnClickListener {
            startActivity(Intent(this, ProductMonitoringActivity::class.java))
        }

        findViewById<Button>(R.id.btnComplaint)?.setOnClickListener {
            startActivity(Intent(this, ComplaintHandlingActivity::class.java))
        }

        findViewById<Button>(R.id.btnSettings)?.setOnClickListener {
            // Note: Using SystemSettingsActivity to avoid conflict with potential AdminSettingsActivity
            startActivity(Intent(this, SystemSettingsActivity::class.java))
        }

        findViewById<Button>(R.id.btnAnalytics)?.setOnClickListener {
            startActivity(Intent(this, AdminAnalyticsActivity::class.java))
        }


        // --- Logout Button ---
=======
        val adminName = intent.getStringExtra("adminName")
        greetingText.text = "Hello, $adminName"

>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
        val logoutOption = findViewById<ImageView>(R.id.logout)
        logoutOption.setOnClickListener {
            auth.signOut()
            // Navigate back to login screen (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
