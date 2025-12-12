package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore // 1. IMPORT FIRESTORE

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    // Initialize Firebase instances
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

=======

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
        val firstNameInput = view.findViewById<EditText>(R.id.firstNameInput)
        val lastNameInput = view.findViewById<EditText>(R.id.lastNameInput)
        val phoneInput = view.findViewById<EditText>(R.id.phoneInput)
        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val genderSpinner = view.findViewById<Spinner>(R.id.genderSpinner)
        val saveButton = view.findViewById<Button>(R.id.saveProfileButton)

<<<<<<< HEAD
        // 🔹 STEP 1: LOAD EXISTING DATA (Crucial for user experience)
        loadUserData(firstNameInput, lastNameInput, phoneInput, emailInput, genderSpinner)

=======
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
        // Setup gender dropdown
        val genderOptions = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        saveButton.setOnClickListener {
<<<<<<< HEAD
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val gender = genderSpinner.selectedItem.toString()

            // Optional: Basic validation
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty() || gender == "Select Gender") {
                Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🚀 STEP 2: SAVE THE UPDATED DATA TO FIRESTORE
            saveUserData(firstName, lastName, phone, email, gender)
        }
    }


    // ====================================================================
    // ⬇️ NEW HELPER FUNCTIONS ⬇️
    // ====================================================================

    private fun loadUserData(
        firstNameInput: EditText,
        lastNameInput: EditText,
        phoneInput: EditText,
        emailInput: EditText,
        genderSpinner: Spinner
    ) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users").document(currentUser.uid).get()
                .addOnSuccessListener { doc ->
                    // Set current values to EditTexts
                    firstNameInput.setText(doc.getString("firstName") ?: "")
                    lastNameInput.setText(doc.getString("lastName") ?: "")
                    phoneInput.setText(doc.getString("phone") ?: "")
                    emailInput.setText(doc.getString("email") ?: "")

                    // Set gender spinner selection
                    val currentGender = doc.getString("gender")
                    val genderOptions = resources.getStringArray(R.array.gender_array) // assuming you define this array
                    val position = genderOptions.indexOf(currentGender)
                    if (position >= 0) {
                        genderSpinner.setSelection(position)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to load existing data.", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun saveUserData(firstName: String, lastName: String, phone: String, email: String, gender: String) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_SHORT).show()
            return
        }

        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "phone" to phone,
            "email" to email,
            "gender" to gender,
            // You can add more fields here if needed
        )

        firestore.collection("users").document(currentUser.uid).update(userData as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_LONG).show()

                // Navigate back to the profile display (FarmerProfileActivity)
                parentFragmentManager.popBackStack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error updating profile: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
=======
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val phone = phoneInput.text.toString()
            val email = emailInput.text.toString()
            val gender = genderSpinner.selectedItem.toString()

            // TODO: Save to Firestore or local storage
            Toast.makeText(requireContext(), "Profile saved!", Toast.LENGTH_SHORT).show()

            parentFragmentManager.popBackStack() // Go back to ProfileFragment
        }
    }
}
>>>>>>> 5b5fe13ff6fb54957cf1e212d8b6fe6ebbcbd977
