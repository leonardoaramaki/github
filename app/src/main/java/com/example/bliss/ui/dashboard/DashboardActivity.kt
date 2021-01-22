package com.example.bliss.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bliss.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDashboardBinding.inflate(layoutInflater)


        setContentView(binding.root)
    }
}