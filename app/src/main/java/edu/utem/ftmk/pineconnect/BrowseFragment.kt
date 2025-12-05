package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PineappleAdapter
    private lateinit var searchBar: EditText
    private val firestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.pineappleRecycler)
        searchBar = view.findViewById(R.id.searchBar)

        adapter = PineappleAdapter(emptyList()) { selectedPineapple ->
            // ✅ Navigate to ProductDetailFragment using newInstance
            val fragment = ProductDetailFragment.newInstance(selectedPineapple)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Load all pineapples initially
        loadPineapples()

        // Search listener
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loadPineapples(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadPineapples(query: String = "") {
        firestore.collection("pineapple_info")
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull { doc ->
                    val name = doc.getString("name") ?: return@mapNotNull null
                    if (query.isEmpty() || name.contains(query, ignoreCase = true)) {
                        Pineapple(
                            name,
                            doc.getString("description") ?: "",
                            doc.getString("image_url") ?: "",
                            doc.getString("origin") ?: "",
                            doc.getDouble("price") ?: 0.0,
                            doc.getString("size") ?: ""
                        )
                    } else null
                }
                adapter.updateData(list)
            }
            .addOnFailureListener { e ->
                android.util.Log.e("FirestoreError", "Failed to load pineapples", e)
            }
    }
}
