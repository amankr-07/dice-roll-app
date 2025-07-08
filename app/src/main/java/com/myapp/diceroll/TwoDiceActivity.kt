package com.myapp.diceroll

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

class TwoDiceActivity : AppCompatActivity() {

    lateinit var imageViewTwoDice1 : ImageView
    lateinit var imageViewTwoDice2 : ImageView
    lateinit var resultTextTwoDice1 : TextView
    lateinit var resultTextTwoDice2 : TextView
    lateinit var buttonRollTwoDice : Button
    lateinit var backButton : ImageButton
    private lateinit var handler: Handler
    private var faceChangeRunnable: Runnable? = null
    private val diceImages = arrayOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_two_dice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handler = Handler(Looper.getMainLooper())

        imageViewTwoDice1  = findViewById(R.id.imageViewTwoDice1)
        imageViewTwoDice2  = findViewById(R.id.imageViewTwoDice2)
        resultTextTwoDice1  = findViewById(R.id.textViewResultTwoDice1)
        resultTextTwoDice2  = findViewById(R.id.textViewResultTwoDice2)
        buttonRollTwoDice  = findViewById(R.id.buttonRollTwoDice)
        backButton = findViewById(R.id.buttonBack)

        buttonRollTwoDice.setOnClickListener {
            rollDice()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun rollDice() {
        buttonRollTwoDice.isEnabled = false

        resultTextTwoDice1.text = ""
        resultTextTwoDice2.text = ""

        val finalResult1 = Random.nextInt(1, 7)
        val finalResult2 = Random.nextInt(1, 7)

        startRollingAnimation1(finalResult1, finalResult2)
    }

    private fun startRollingAnimation1(finalResult1: Int, finalResult2: Int) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = Animation.INFINITE
        }

        imageViewTwoDice1.startAnimation(rotateAnimation)
        startFaceChangingAnimation1()

        handler.postDelayed({
            stopRollingAnimation1(finalResult1, finalResult2)
        }, 1000)
    }

    private fun startFaceChangingAnimation1() {
        faceChangeRunnable = object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(diceImages.size)
                imageViewTwoDice1.setImageResource(diceImages[randomIndex])
                handler.postDelayed(this, 100)
            }
        }
        handler.post(faceChangeRunnable!!)
    }

    private fun stopRollingAnimation1(finalResult1: Int, finalResult2: Int) {
        imageViewTwoDice1.clearAnimation()
        faceChangeRunnable?.let { handler.removeCallbacks(it) }

        imageViewTwoDice1.setImageResource(diceImages[finalResult1 - 1])
        showFinalResult1(finalResult1)

        handler.postDelayed({
            startRollingAnimation2(finalResult2)
        }, 200)
    }

    private fun startRollingAnimation2(finalResult2: Int) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = Animation.INFINITE
        }

        imageViewTwoDice2.startAnimation(rotateAnimation)
        startFaceChangingAnimation2()

        handler.postDelayed({
            stopRollingAnimation2(finalResult2)
        }, 1000)
    }

    private fun startFaceChangingAnimation2() {
        faceChangeRunnable = object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(diceImages.size)
                imageViewTwoDice2.setImageResource(diceImages[randomIndex])
                handler.postDelayed(this, 100)
            }
        }
        handler.post(faceChangeRunnable!!)
    }

    private fun stopRollingAnimation2(finalResult2: Int) {
        imageViewTwoDice2.clearAnimation()
        faceChangeRunnable?.let { handler.removeCallbacks(it) }

        imageViewTwoDice2.setImageResource(diceImages[finalResult2 - 1])
        showFinalResult2(finalResult2)

        buttonRollTwoDice.isEnabled = true
    }

    private fun showFinalResult1(result: Int) {
        resultTextTwoDice1.text = "You rolled: $result"

        resultTextTwoDice1.alpha = 0f
        resultTextTwoDice1.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }

    private fun showFinalResult2(result: Int) {
        resultTextTwoDice2.text = "You rolled: $result"

        resultTextTwoDice2.alpha = 0f
        resultTextTwoDice2.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }
}