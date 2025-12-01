package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.DataAsistenScreen
import com.example.monpad.viewmodel.AssistantViewModel

class Asisten : ComponentActivity() {

    private val assistantViewModel: AssistantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DataAsistenScreen(assistantViewModel)
        }

        // Fetch data asisten
        assistantViewModel.getAssistants()
    }
}