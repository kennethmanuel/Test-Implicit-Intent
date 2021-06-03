package com.kennethmanuel.a160419041_week12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_message_template.*

class MessageTemplateActivity : AppCompatActivity() {
    companion object {
        val EXTRA_TEMPLATE = "TEMPLATE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_template)

        val templates: ArrayList<String> =
            arrayListOf(
                "Whatsup?",
                "???",
                "Yes?",
                "Dota?",
                "Test123",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam felis erat, vestibulum laoreet nunc vel, laoreet porta sapien. Proin vel placerat nibh, id ullamcorper tortor. Praesent eu interdum neque. Suspendisse nisi quam, tristique laoreet suscipit in, efficitur non massa. Nam iaculis lectus tempus, efficitur arcu id, lacinia libero.",
                "Nak, ini mama, mama sekarang lagi di kantor polisi, mama butuh uang tebusan supaya malam ini mama nggak nginap di penjara. Kirimin mama saldo OVO ya, satu juta aja. Cepetan ya!!!",
            )
        var lm = LinearLayoutManager(this)
        with(recyclerView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = TemplateAdapter(templates, this@MessageTemplateActivity)
        }
    }
}