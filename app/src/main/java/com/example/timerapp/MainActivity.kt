package com.example.timerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var seconds: Int = 0;
    private var isRunning: Boolean = false
    lateinit var TVTimer: TextView;
    private var wasRunning = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TVTimer = findViewById(R.id.TVTimer)
        runTimer()
        if(savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean("isRunning")
            seconds = savedInstanceState.getInt("seconds")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
    }

  /*  override fun onStop() {
        super.onStop()
        wasRunning = isRunning
        isRunning = false
    }*/

    override fun onPause() {
        super.onPause()
        wasRunning = isRunning
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = wasRunning
    }

   /* override fun onStart() {
        super.onStart()
        isRunning = wasRunning
    }*/

    fun onClickStartTimer(view: View) {
        isRunning = true;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("isRunning", isRunning)
        outState.putBoolean("wasRunning", wasRunning)
    }

    fun onClickPauseTimer(view: View) {
        isRunning = false;
    }

    fun onClickResetTimer(view: View) {
        isRunning = false;
        seconds = 0;
    }

    private fun runTimer() {
        val handler: Handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val hours = seconds / 3600;
                val minutes = (seconds % 3600) / 60;
                var secs = (seconds % 60)
                val time: String = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
                TVTimer.text = time
                if (isRunning) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)

    }
}