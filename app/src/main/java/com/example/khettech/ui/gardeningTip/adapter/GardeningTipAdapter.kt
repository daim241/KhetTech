package com.example.khettech.ui.gardeningTip.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khettech.R
import com.example.khettech.models.GardeningTip

class GardeningTipAdapter(
    private val context: Context,
    private val tipsList: List<GardeningTip>
) : RecyclerView.Adapter<GardeningTipAdapter.TipViewHolder>() {

    inner class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvDescription)
        val urlTextView: TextView = itemView.findViewById(R.id.tvUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gardening_tip_adapter, parent, false)
        return TipViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val currentTip = tipsList[position]
        holder.titleTextView.text = currentTip.title
        holder.descriptionTextView.text = currentTip.description

        val url = currentTip.videoUrl
        val spannableString = SpannableString(url)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }
        spannableString.setSpan(clickableSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        holder.urlTextView.text = spannableString
        holder.urlTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun getItemCount() = tipsList.size
}
