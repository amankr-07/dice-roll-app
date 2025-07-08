package com.myapp.diceroll

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class OneDiceActivity : AppCompatActivity() {

    lateinit var imageViewOneDice : ImageView
    lateinit var resultTextOneDice : TextView
    lateinit var buttonRollOneDice : Button
    lateinit var backButton : ImageButton
    private lateinit var handler: Handler
    private val diceImages = arrayOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_one_dice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handler = Handler(Looper.getMainLooper())

        imageViewOneDice  = findViewById(R.id.imageViewOneDice)
        resultTextOneDice  = findViewById(R.id.textViewResultOneDice)
        buttonRollOneDice = findViewById(R.id.buttonRollOneDice)
        backButton = findViewById(R.id.buttonBack)

        buttonRollOneDice.setOnClickListener {
            rollDice()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun rollDice() {
        buttonRollOneDice.isEnabled = false

        resultTextOneDice.text = ""

        startRollingAnimation()

        val finalResult = Random.nextInt(1, 7)

        handler.postDelayed({
            stopRollingAnimation(finalResult)
        }, 1000)
    }

    private fun startRollingAnimation() {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = Animation.INFINITE
        }

        imageViewOneDice.startAnimation(rotateAnimation)

        startFaceChangingAnimation()
    }

    private fun startFaceChangingAnimation() {
        val runnable = object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(diceImages.size)
                imageViewOneDice.setImageResource(diceImages[randomIndex])

                handler.postDelayed(this, 100)
            }
        }
        handler.post(runnable)
    }

    private fun stopRollingAnimation(finalResult: Int) {
        imageViewOneDice.clearAnimation()
        handler.removeCallbacksAndMessages(null)

        imageViewOneDice.setImageResource(diceImages[finalResult - 1])

        showFinalResult(finalResult)

        buttonRollOneDice.isEnabled = true
    }

    private fun showFinalResult(result: Int) {
        resultTextOneDice.text = "You rolled: $result"

        resultTextOneDice.alpha = 0f
        resultTextOneDice.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }

}