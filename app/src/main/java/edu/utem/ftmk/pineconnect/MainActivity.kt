package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val emailField = findViewById<EditText>(R.id.editTextEmail)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtSignUp = findViewById<TextView>(R.id.txtRegister)

        txtSignUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            // 🔐 Admin login (fixed credentials)
            if (email == "admin@pineconnect.com" && password == "admin123") {
                // fetch admin profile from Firestore
                firestore.collection("users").document("admin001").get()
                    .addOnSuccessListener { doc ->
                        val name = doc.getString("name") ?: "Admin"
                        val intent = Intent(this, AdminDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }
            }


            // 🔐 Firebase login for customer/farmer
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener
                firestore.collection("users").document(uid).get().addOnSuccessListener { doc ->
                    val role = doc.getString("role")
                    when (role) {
                        "customer" -> startActivity(Intent(this, CustomerDashboard::class.java))
                        "farmer" -> startActivity(Intent(this, FarmerDashboard::class.java))
                        else -> Toast.makeText(this, "Unknown role", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
