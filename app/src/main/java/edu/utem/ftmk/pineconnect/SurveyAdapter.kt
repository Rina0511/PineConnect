package edu.utem.ftmk.pineconnect

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SurveyAdapter(private val surveys: List<Survey>) :
    RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder>() {

    class SurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPeriod: TextView = itemView.findViewById(R.id.textSurveyPeriod)
        val textStatus: TextView = itemView.findViewById(R.id.textSurveyStatus)
        val container: LinearLayout = itemView.findViewById(R.id.surveyItemContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_survey, parent, false)
        return SurveyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val survey = surveys[position]

        // Set text and dynamic status color
        holder.textPeriod.text = survey.period

        if (survey.isCompleted) {
            holder.textStatus.text = "Status: COMPLETED"
            holder.textStatus.setTextColor(Color.parseColor("#4CAF50")) // Green
        } else {
            holder.textStatus.text = "Status: PENDING"
            holder.textStatus.setTextColor(Color.parseColor("#FF9800")) // Orange
        }

        // Add click listener to launch the form
        holder.container.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, YieldSurveyFormActivity::class.java)
            intent.putExtra("SURVEY_ID", survey.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = surveys.size
}