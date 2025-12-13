package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.ProjectGroupScreen
import com.example.monpad.jpcompose.ProjectGroupScreenAst
import com.example.monpad.viewmodel.ProjectViewModel

class ProjectAsistenActivity : ComponentActivity() {
    private val projectViewModel: ProjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectGroupScreenAst(projectViewModel)
        }
        projectViewModel.getProjects()
    }
}