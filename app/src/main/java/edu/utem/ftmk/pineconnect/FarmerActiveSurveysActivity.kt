package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FarmerActiveSurveysActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var surveyList: MutableList<Survey>
    private lateinit var adapter: SurveyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_active_surveys)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Setup Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewActiveSurveys)
        surveyList = mutableListOf()
        adapter = SurveyAdapter(surveyList)
        recyclerView.adapter = adapter

        // Setup Button
        val startButton = findViewById<Button>(R.id.btnStartMonthlySurvey)
        startButton.setOnClickListener {
            startMonthlyYieldSurvey()
        }

        loadActiveSurveys()
    }

    // Function to fetch survey data from Firestore
    private fun loadActiveSurveys() {
        val farmerId = auth.currentUser?.uid
        if (farmerId == null) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show()
            return
        }

        // **IMPORTANT: MAKE SURE YOU USE THE CORRECT CASING HERE**
        // Use farmerID, isCompleted, period, etc., based on your database structure
        firestore.collection("surveys")
            .whereEqualTo("farmerID", farmerId)
            .orderBy("isCompleted", Query.Direction.ASCENDING)
            .orderBy("period", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                surveyList.clear()
                // --- ADD LOGGING HERE ---
                android.util.Log.d("SurveyLoad", "Documents found: ${documents.size()}")
                // -------------------------

                for (doc in documents) {
                    val survey = Survey(
                        id = doc.id,
                        period = doc.getString("period") ?: "N/A",
                        isCompleted = doc.getBoolean("isCompleted") ?: false
                    )
                    surveyList.add(survey)
                }
                adapter.notifyDataSetChanged()

                if (surveyList.isEmpty()) {
                    Toast.makeText(this, "No active surveys found.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // --- ADD BETTER ERROR LOGGING HERE ---
                android.util.Log.e("SurveyLoad", "Query failed!", e)
                Toast.makeText(this, "Failed to load surveys: ${e.message}", Toast.LENGTH_LONG)
                    .show()
                // -------------------------------------
            }
    }

    // Logic for the main button click (same as before, but improved)
    private fun startMonthlyYieldSurvey() {
        val intent = Intent(this, YieldSurveyFormActivity::class.java)
        // You might pass data here, but for now, it just launches the form
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
