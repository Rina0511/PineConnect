package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SettingFragment : Fragment(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.optionRateApp).setOnClickListener {
            Toast.makeText(requireContext(), "Redirecting to Play Store...", Toast.LENGTH_SHORT).show()
            // TODO: Replace with actual Play Store link
        }

        view.findViewById<TextView>(R.id.optionShareApp).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Check out PineConnect!")
            startActivity(Intent.createChooser(intent, "Share via"))
        }

        view.findViewById<TextView>(R.id.optionPrivacyPolicy).setOnClickListener {
            // TODO: Open privacy policy URL
        }

        view.findViewById<TextView>(R.id.optionTerms).setOnClickListener {
            // TODO: Open terms and conditions URL
        }

        view.findViewById<TextView>(R.id.optionFeedback).setOnClickListener {
            Toast.makeText(requireContext(), "Opening feedback form...", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to feedback screen or open form
        }

        view.findViewById<TextView>(R.id.optionLogout).setOnClickListener {
            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show()
            // TODO: Clear session and navigate to login
        }
    }
}
