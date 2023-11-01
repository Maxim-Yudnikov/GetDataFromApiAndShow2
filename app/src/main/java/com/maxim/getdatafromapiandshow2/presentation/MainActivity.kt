package com.maxim.getdatafromapiandshow2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.maxim.getdatafromapiandshow2.App
import com.maxim.getdatafromapiandshow2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val actionButton = findViewById<Button>(R.id.actionButton)
        val viewModel = (application as App).viewModel

        actionButton.setOnClickListener {
            viewModel.getFact()
        }

        viewModel.observe(this) {
            it.show(textView, progressBar, actionButton)
        }
    }
}