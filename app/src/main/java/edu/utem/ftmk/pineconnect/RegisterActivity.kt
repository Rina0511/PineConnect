package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val nameField = findViewById<EditText>(R.id.editTextName)
        val emailField = findViewById<EditText>(R.id.editTextEmail)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupUserType)
        val farmerIdField = findViewById<EditText>(R.id.editTextFarmerID)
        val btnSignUp = findViewById<Button>(R.id.buttonSignUp)

        btnSignUp.setOnClickListener {
            val name = nameField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            // get selected role
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRole = findViewById<RadioButton>(selectedId)?.text.toString().lowercase()

            // optional farmer ID
            val farmerID = if (selectedRole == "farmer") farmerIdField.text.toString() else null

            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener
                val userData = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "role" to selectedRole,
                    "uid" to uid
                )
                if (farmerID != null) {
                    userData["farmerID"] = farmerID
                }

                firestore.collection("users").document(uid).set(userData).addOnSuccessListener {
                    Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java)) // go to login page
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Registration failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
