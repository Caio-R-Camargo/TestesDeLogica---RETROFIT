package com.android.testesdelgica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.testesdelgica.BuildConfig
import com.android.testesdelgica.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textVersion.text = "ver" + BuildConfig.VERSION_NAME

        supportActionBar?.hide()
        setListeners()

    }

    private fun setListeners() {
        binding.buttonSoma.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Toast.makeText(applicationContext, "Soma selecionado", Toast.LENGTH_LONG).show()
        }
    }

}