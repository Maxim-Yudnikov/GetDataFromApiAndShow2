package com.maxim.getdatafromapiandshow2.presentation

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

interface State {
    fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button)
    object Progress: State {
        override fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            progressBar.visibility = View.VISIBLE
            actionButton.isEnabled = false
        }
    }

    data class Success(private val text: String): State {
        override fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            textView.text = text
            progressBar.visibility = View.INVISIBLE
            actionButton.isEnabled = true
        }
    }

    data class Failed(private val text: String): State {
        override fun show(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            textView.text = text
            progressBar.visibility = View.INVISIBLE
            actionButton.isEnabled = true
        }
    }
}