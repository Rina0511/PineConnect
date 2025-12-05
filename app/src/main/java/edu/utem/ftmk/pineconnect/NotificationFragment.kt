package edu.utem.ftmk.pineconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyLayout: View
    private lateinit var adapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.notificationRecycler)
        emptyLayout = view.findViewById(R.id.emptyStateLayout)

        val notifications = loadNotifications()

        adapter = NotificationAdapter(notifications)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Toggle empty state
        if (notifications.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyLayout.visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.historicalLink).setOnClickListener {
            Toast.makeText(requireContext(), "Opening historical notifications...", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to historical notifications screen
        }
    }

    private fun loadNotifications(): List<NotificationItem> {
        // TODO: Replace with Firestore or local data
        return listOf(
            NotificationItem("Payment pending", "Your payment for Order #1234 is still pending.", "Dec 4, 2025"),
            NotificationItem("Order out for delivery", "Your order #1234 is on the way!", "Dec 3, 2025")
        )
    }
}

