package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var bannerViewPager: ViewPager
    private lateinit var bannerIndicator: TabLayout
    private lateinit var handler: Handler
    private var currentPage = 0
    private val delay: Long = 3000 // 3 seconds

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val greetingText = rootView.findViewById<TextView>(R.id.homeGreeting)

        // 🔹 Fetch current user name
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    val name = doc.getString("name")
                    if (!name.isNullOrEmpty()) {
                        greetingText.text = "Hello, $name"
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to load user info", Toast.LENGTH_SHORT).show()
                }
        }

        // Banner setup
        bannerViewPager = rootView.findViewById(R.id.bannerViewPager)
        bannerIndicator = rootView.findViewById(R.id.bannerIndicator)

        val images = listOf(
            R.drawable.banner,
            R.drawable.banner2,
            R.drawable.banner3
        )

        val adapter = BannerAdapter(requireContext(), images)
        bannerViewPager.adapter = adapter
        bannerIndicator.setupWithViewPager(bannerViewPager)

        handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == images.size) {
                    currentPage = 0
                }
                bannerViewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, delay)
            }
        }
        handler.post(runnable)

        // Browse Pineapple card click
        val browseCard = rootView.findViewById<CardView>(R.id.browseCard)
        browseCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BrowseFragment())
                .addToBackStack(null)
                .commit()
        }

        // My Orders card click
        val ordersCard = rootView.findViewById<CardView>(R.id.ordersCard)
        ordersCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, OrdersFragment())
                .addToBackStack(null)
                .commit()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
