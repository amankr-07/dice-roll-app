package com.myapp.diceroll

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var buttonOne : Button = findViewById(R.id.buttonOne)
        var buttonTwo : Button = findViewById(R.id.buttonTwo)
        var buttonThree : Button = findViewById(R.id.buttonThree)

        buttonOne.setOnClickListener {
            val intent = Intent(this, OneDiceActivity::class.java)
            startActivity(intent)
        }

        buttonTwo.setOnClickListener {
            val intent = Intent(this, TwoDiceActivity::class.java)
            startActivity(intent)
        }

        buttonThree.setOnClickListener {
            val intent = Intent(this, ThreeDiceActivity::class.java)
            startActivity(intent)
        }

    }
}