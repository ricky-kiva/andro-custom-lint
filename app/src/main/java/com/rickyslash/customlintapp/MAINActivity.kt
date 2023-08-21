package com.rickyslash.customlintapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MAINActivity : AppCompatActivity() { // warning on class naming after custom Lint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}