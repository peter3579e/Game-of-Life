package com.peter.gameoflife


import android.os.Bundle;
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var gameOfLifeView: GameOfLifeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameOfLifeView = findViewById<View>(R.id.game_of_life) as GameOfLifeView
    }

    override fun onResume() {
        super.onResume()
        gameOfLifeView!!.start()
    }

    override fun onPause() {
        super.onPause()
        gameOfLifeView!!.stop()
    }
}