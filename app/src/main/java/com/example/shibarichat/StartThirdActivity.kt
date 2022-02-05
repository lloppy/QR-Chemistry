package com.example.shibarichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_third)
        supportActionBar?.hide()


        var button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            val intent = Intent(this, StartFourActivity::class.java)
            startActivity(intent)
        }
    }
}