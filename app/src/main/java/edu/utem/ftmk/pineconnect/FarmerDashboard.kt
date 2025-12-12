package edu.utem.ftmk.pineconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// ======================================================
// ✅ CRITICAL FIX: Missing Activity Imports
// These imports are necessary for the compiler to find the Activity classes.
// ======================================================
import edu.utem.ftmk.pineconnect.FarmerProfileActivity
import edu.utem.ftmk.pineconnect.FarmerActiveSurveysActivity
import edu.utem.ftmk.pineconnect.FarmerAnalyticsActivity
import edu.utem.ftmk.pineconnect.FarmerCustomerInteractionActivity
import edu.utem.ftmk.pineconnect.FarmerNotificationsActivity
import edu.utem.ftmk.pineconnect.FarmerKnowledgeHubActivity
import edu.utem.ftmk.pineconnect.FarmerSupportActivity
import edu.utem.ftmk.pineconnect.FarmerSettingsActivity

// Tag for logging messages
private const val TAG = "FarmerDashboard"

class FarmerDashboard : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    // These are initialized using findViewById(R.id.textSurveyStatus)
    // and findViewById(R.id.textLatestYield) which are correct in the XML.
    private lateinit var textSurveyStatus: TextView
    private lateinit var textLatestYield: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_dashboard)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize new TextViews (IDs are correct as per your XML)
        textSurveyStatus = findViewById(R.id.textSurveyStatus)
        textLatestYield = findViewById(R.id.textLatestYield)

        // Initial setup only, actual loading moved to onResume()
        val greetingText = findViewById<TextView>(R.id.farmerGreeting)
        val currentUserId = auth.currentUser?.uid

        currentUserId?.let { uid ->
            // Load user name immediately
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    greetingText.text = "Hello, ${document.getString("name") ?: "Farmer"}"
                }
        }

        // ======================================================
        // ✅ FINAL CORRECTED PORTAL CLICK ACTIONS
        // All IDs are checked against your activity_farmer_dashboard.xml
        // ======================================================

        // 1. Profile Icon (Top Right)
        findViewById<ImageView>(R.id.iconProfile).setOnClickListener {
            startActivity(Intent(this, FarmerProfileActivity::class.java))
        }

        // 2. Farmer Profile Card (Grid)
        // XML ID: portalFarmProfile
        findViewById<CardView>(R.id.portalFarmProfile).setOnClickListener {
            startActivity(Intent(this, FarmerProfileActivity::class.java))
        }

        // 3. Active Surveys Portal (ID Correct)
        // XML ID: portalActiveSurveys
        findViewById<CardView>(R.id.portalActiveSurveys).setOnClickListener {
            startActivity(Intent(this, FarmerActiveSurveysActivity::class.java))
        }

        // 4. Yield/Analytics Portal
        // ❌ FIX 1: Changed from 'portalYieldAnalytics' to 'portalAnalytics' to match XML.
        findViewById<CardView>(R.id.portalAnalytics).setOnClickListener {
            startActivity(Intent(this, FarmerAnalyticsActivity::class.java))
        }

        // 5. Customer Interaction Portal
        // ❌ FIX 2: Corrected Activity name to FarmerCustomerInteractionActivity.
        findViewById<CardView>(R.id.portalCustomerInteractions).setOnClickListener {
            startActivity(Intent(this, FarmerCustomerInteractionActivity::class.java))
        }

        // 6. Notifications Portal
        // ❌ FIX 3: Corrected Activity name to FarmerNotificationsActivity.
        findViewById<CardView>(R.id.portalNotifications).setOnClickListener {
            startActivity(Intent(this, FarmerNotificationsActivity::class.java))
        }

        // 7. Knowledge Hub Portal
        // ❌ FIX 4: Corrected Activity name to FarmerKnowledgeHubActivity.
        findViewById<CardView>(R.id.portalKnowledgeHub).setOnClickListener {
            startActivity(Intent(this, FarmerKnowledgeHubActivity::class.java))
        }

        // 8. Support & Report Portal
        // ❌ FIX 5: Corrected Activity name to FarmerSupportActivity.
        // XML ID: portalSupport
        findViewById<CardView>(R.id.portalSupport).setOnClickListener {
            startActivity(Intent(this, FarmerSupportActivity::class.java))
        }

        // 9. Settings Icon (Top Left)
        // ❌ FIX 6: Corrected Activity name to FarmerSettingsActivity.
        findViewById<ImageView>(R.id.iconSettings).setOnClickListener {
            startActivity(Intent(this, FarmerSettingsActivity::class.java))
        }

        // 10. Settings/Account Card (Grid)
        // ❌ FIX 7: Corrected Activity name to FarmerSettingsActivity.
        // XML ID: portalSettingsAccount
        findViewById<CardView>(R.id.portalSettingsAccount).setOnClickListener {
            startActivity(Intent(this, FarmerSettingsActivity::class.java))
        }
    }

    // ======================================================
    // ⬇️ Data Refresh on Resume ⬇️
    // ======================================================
    override fun onResume() {
        super.onResume()
        val currentUserId = auth.currentUser?.uid
        currentUserId?.let { uid ->
            // Reload the data every time the activity comes to the foreground
            loadSurveyStatus(uid)
            loadLatestYield(uid)
        }
    }


    // ======================================================
    // ✅ FIX: Load Survey Status (Casing Fixed)
    // ======================================================
    private fun loadSurveyStatus(farmerId: String) {
        firestore.collection("surveys")
            // CRITICAL FIX: Changed to "farmerID" (Uppercase D) to match database
            .whereEqualTo("farmerID", farmerId)
            .orderBy("period", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    textSurveyStatus.text = "No Active Survey"
                    textSurveyStatus.setTextColor(Color.GRAY)
                    return@addOnSuccessListener
                }

                val surveyDoc = documents.documents.first()
                val isCompleted = surveyDoc.getBoolean("isCompleted") ?: false
                val period = surveyDoc.getString("period") ?: "Current"

                if (isCompleted) {
                    textSurveyStatus.text = "$period: COMPLETED"
                    textSurveyStatus.setTextColor(Color.parseColor("#4CAF50"))
                } else {
                    textSurveyStatus.text = "$period: PENDING"
                    textSurveyStatus.setTextColor(Color.parseColor("#FF9800"))
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to load survey status. Check Firestore index.", e)
                textSurveyStatus.text = "Status Error"
                textSurveyStatus.setTextColor(Color.RED)
            }
    }

    // ======================================================
    // ✅ FIX: Load Latest Yield Data (Casing Fixed)
    // ======================================================
    private fun loadLatestYield(farmerId: String) {
        firestore.collection("yield_reports")
            // CRITICAL FIX: Changed to "farmerID" (Uppercase D) to match database
            .whereEqualTo("farmerID", farmerId)
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    textLatestYield.text = "Yield: N/A"
                    return@addOnSuccessListener
                }

                val yieldDoc = documents.documents.first()
                val amount = yieldDoc.getDouble("amount") ?: 0.0
                val unit = yieldDoc.getString("unit") ?: "kg"

                textLatestYield.text = "Latest Yield: ${String.format("%.2f", amount)} $unit"
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to load latest yield. Check Firestore index.", e)
                textLatestYield.text = "Yield Error"
            }
    }
}