package com.maxim.getdatafromapiandshow2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maxim.getdatafromapiandshow2.App
import com.maxim.getdatafromapiandshow2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val actionButton = findViewById<Button>(R.id.actionButton)
        val favoriteButton = findViewById<Button>(R.id.favoriteButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val viewModel = (application as App).viewModel

        actionButton.setOnClickListener {
            viewModel.getFact()
        }

        favoriteButton.setOnClickListener {
            viewModel.saveFact()
            favoriteButton.isEnabled = false
        }

        viewModel.observe(this) {
            it.show(textView, progressBar, actionButton, favoriteButton)
        }

        val adapter = RecyclerViewAdapter(viewModel.communication)
        recyclerView.adapter = adapter
        viewModel.observeList(this) {
            adapter.update()
        }
        viewModel.getItemList()
    }
}