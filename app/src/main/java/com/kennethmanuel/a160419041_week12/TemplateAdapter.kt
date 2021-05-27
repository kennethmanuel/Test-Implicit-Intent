package com.kennethmanuel.a160419041_week12

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_template.view.*

class TemplateAdapter(val templates: ArrayList<String>, val activity: Activity) :
    RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder>() {
    class TemplateViewHolder(var view: View, var activity: Activity) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_template, parent, false)
        return TemplateViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
        with(holder) {
            view.textTemplate.text = templates[position]
            view.buttonPilih.setOnClickListener {
                val intent = Intent().apply {
                    putExtra(MessageTemplateActivity.EXTRA_TEMPLATE, templates[position])
                }
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }
    }

    override fun getItemCount() = templates.size

}