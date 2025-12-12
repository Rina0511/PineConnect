// File: YieldSurveyFormActivity.kt
package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class YieldSurveyFormActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var inputHarvestDate: EditText
    private lateinit var inputYieldAmount: EditText
    private lateinit var btnSubmitYield: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yield_survey_form)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize UI components
        inputHarvestDate = findViewById(R.id.inputHarvestDate)
        inputYieldAmount = findViewById(R.id.inputYieldAmount)
        btnSubmitYield = findViewById(R.id.btnSubmitYield)

        // Handle date picker logic (User must tap the EditText to select date)
        inputHarvestDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnSubmitYield.setOnClickListener {
            submitYieldReport()
        }
    }

    private fun showDatePickerDialog() {
        // Basic date picker implementation (requires importing appropriate library or using AlertDialog/DatePickerDialog)
        // For simplicity here, we'll just show a placeholder message.
        // In a real app, you would use DatePickerDialog to set the date.
        Toast.makeText(this, "Date picker would open here.", Toast.LENGTH_SHORT).show()

        // Example: Automatically setting today's date for testing
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        inputHarvestDate.setText(currentDate)
    }

    private fun submitYieldReport() {
        val farmerId = auth.currentUser?.uid
        if (farmerId == null) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show()
            return
        }

        val dateString = inputHarvestDate.text.toString()
        val amountString = inputYieldAmount.text.toString()

        if (dateString.isEmpty() || amountString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountString.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Invalid yield amount.", Toast.LENGTH_SHORT).show()
            return
        }

        val reportData = hashMapOf(
            "farmerId" to farmerId,
            "date" to dateString, // Storing as string for simplicity, Timestamp is better for real data
            "amount" to amount,
            "unit" to "kg",
            "timestamp" to System.currentTimeMillis()
        )

        // 1. Save the new yield report
        firestore.collection("yield_reports").add(reportData)
            .addOnSuccessListener {
                updateSurveyCompletionStatus(farmerId)
                Toast.makeText(this, "Yield report submitted!", Toast.LENGTH_LONG).show()
                finish() // Close the form
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Submission failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateSurveyCompletionStatus(farmerId: String) {
        // 2. Find the current pending survey and mark it as completed
        firestore.collection("surveys")
            .whereEqualTo("farmerId", farmerId)
            .whereEqualTo("isCompleted", false)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val surveyDocId = documents.documents.first().id
                    firestore.collection("surveys").document(surveyDocId)
                        .update("isCompleted", true)
                        .addOnFailureListener {
                            // Log or handle the failure to update survey status
                        }
                }
            }
    }
}