package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstNameInput = view.findViewById<EditText>(R.id.firstNameInput)
        val lastNameInput = view.findViewById<EditText>(R.id.lastNameInput)
        val phoneInput = view.findViewById<EditText>(R.id.phoneInput)
        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val genderSpinner = view.findViewById<Spinner>(R.id.genderSpinner)
        val saveButton = view.findViewById<Button>(R.id.saveProfileButton)

        // Setup gender dropdown
        val genderOptions = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        saveButton.setOnClickListener {
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
