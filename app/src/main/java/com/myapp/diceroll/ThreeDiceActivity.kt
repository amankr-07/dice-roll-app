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

class ThreeDiceActivity : AppCompatActivity() {

    lateinit var imageViewThreeDice1 : ImageView
    lateinit var imageViewThreeDice2 : ImageView
    lateinit var imageViewThreeDice3 : ImageView
    lateinit var resultTextThreeDice1 : TextView
    lateinit var resultTextThreeDice2 : TextView
    lateinit var resultTextThreeDice3 : TextView
    lateinit var buttonRollThreeDice : Button
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
        setContentView(R.layout.activity_three_dice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handler = Handler(Looper.getMainLooper())

        imageViewThreeDice1  = findViewById(R.id.imageViewThreeDice1)
        imageViewThreeDice2  = findViewById(R.id.imageViewThreeDice2)
        imageViewThreeDice3  = findViewById(R.id.imageViewThreeDice3)
        resultTextThreeDice1  = findViewById(R.id.textViewResultThreeDice1)
        resultTextThreeDice2  = findViewById(R.id.textViewResultThreeDice2)
        resultTextThreeDice3  = findViewById(R.id.textViewResultThreeDice3)
        buttonRollThreeDice  = findViewById(R.id.buttonRollThreeDice)
        backButton = findViewById(R.id.buttonBack)

        buttonRollThreeDice.setOnClickListener {
            rollDice()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun rollDice() {
        buttonRollThreeDice.isEnabled = false

        resultTextThreeDice1.text = ""
        resultTextThreeDice2.text = ""
        resultTextThreeDice3.text = ""

        val finalResult1 = Random.nextInt(1, 7)
        val finalResult2 = Random.nextInt(1, 7)
        val finalResult3 = Random.nextInt(1, 7)

        startRollingAnimation1(finalResult1, finalResult2, finalResult3)
    }

    private fun startRollingAnimation1(finalResult1: Int, finalResult2: Int, finalResult3: Int) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = Animation.INFINITE
        }

        imageViewThreeDice1.startAnimation(rotateAnimation)
        startFaceChangingAnimation1()

        handler.postDelayed({
            stopRollingAnimation1(finalResult1, finalResult2, finalResult3)
        }, 1000)
    }

    private fun startFaceChangingAnimation1() {
        faceChangeRunnable = object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(diceImages.size)
                imageViewThreeDice1.setImageResource(diceImages[randomIndex])
                handler.postDelayed(this, 100)
            }
        }
        handler.post(faceChangeRunnable!!)
    }

    private fun stopRollingAnimation1(finalResult1: Int, finalResult2: Int, finalResult3: Int) {
        imageViewThreeDice1.clearAnimation()
        faceChangeRunnable?.let { handler.removeCallbacks(it) }

        imageViewThreeDice1.setImageResource(diceImages[finalResult1 - 1])
        showFinalResult1(finalResult1)

        handler.postDelayed({
            startRollingAnimation2(finalResult2, finalResult3)
        }, 200)
    }

    private fun startRollingAnimation2(finalResult2: Int, finalResult3: Int) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = Animation.INFINITE
        }

        imageViewThreeDice2.startAnimation(rotateAnimation)
        startFaceChangingAnimation2()

        handler.postDelayed({
            stopRollingAnimation2(finalResult2, finalResult3)
        }, 1000)
    }

    private fun startFaceChangingAnimation2() {
        faceChangeRunnable = object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(diceImages.size)
                imageViewThreeDice2.setImageResource(diceImages[randomIndex])
                handler.postDelayed(this, 100)
            }
        }
        handler.post(faceChangeRunnable!!)
    }

    private fun stopRollingAnimation2(finalResult2: Int, finalResult3: Int) {
        imageViewThreeDice2.clearAnimation()
        faceChangeRunnable?.let { handler.removeCallbacks(it) }

        imageViewThreeDice2.setImageResource(diceImages[finalResult2 - 1])
        showFinalResult2(finalResult2)

        handler.postDelayed({
            startRollingAnimation3(finalResult3)
        }, 200)
    }

    private fun startRollingAnimation3(finalResult3: Int) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = Animation.INFINITE
        }

        imageViewThreeDice3.startAnimation(rotateAnimation)
        startFaceChangingAnimation3()

        handler.postDelayed({
            stopRollingAnimation3(finalResult3)
        }, 1000)
    }

    private fun startFaceChangingAnimation3() {
        faceChangeRunnable = object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(diceImages.size)
                imageViewThreeDice3.setImageResource(diceImages[randomIndex])
                handler.postDelayed(this, 100)
            }
        }
        handler.post(faceChangeRunnable!!)
    }

    private fun stopRollingAnimation3(finalResult3: Int) {
        imageViewThreeDice3.clearAnimation()
        faceChangeRunnable?.let { handler.removeCallbacks(it) }

        imageViewThreeDice3.setImageResource(diceImages[finalResult3 - 1])
        showFinalResult3(finalResult3)

        buttonRollThreeDice.isEnabled = true
    }

    private fun showFinalResult1(result: Int) {
        resultTextThreeDice1.text = "You rolled: $result"

        resultTextThreeDice1.alpha = 0f
        resultTextThreeDice1.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }

    private fun showFinalResult2(result: Int) {
        resultTextThreeDice2.text = "You rolled: $result"

        resultTextThreeDice2.alpha = 0f
        resultTextThreeDice2.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }

    private fun showFinalResult3(result: Int) {
        resultTextThreeDice3.text = "You rolled: $result"

        resultTextThreeDice3.alpha = 0f
        resultTextThreeDice3.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }
}