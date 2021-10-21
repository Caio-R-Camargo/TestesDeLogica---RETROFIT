package com.android.testesdelgica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.android.testesdelgica.*
import com.android.testesdelgica.databinding.ActivityInitBinding
import java.util.concurrent.Executor

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private lateinit var mSecurityPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSecurityPreferences = SharedPreferences(this)

        supportActionBar?.hide()
        setListeners()
        authentication()
        darkModeValidation()

    }

    private fun authentication(){
        if(FingerprintHelper.authenticationAvailable(this)){

            val executor: Executor = ContextCompat.getMainExecutor(this)
            val biometricPrompt = BiometricPrompt(this@InitActivity, executor, object : BiometricPrompt.AuthenticationCallback(){

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                }
            })
            val info: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.verificacao))
                .setDescription(getString(R.string.dedo_sensor))
                .setNegativeButtonText(getString(R.string.cancelar))
                .build()

            biometricPrompt.authenticate(info)

        }

    }

    private fun darkModeValidation(){
        if (mSecurityPreferences.getBoolean(DevelopConstants.KEY.DARKMODE)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    private fun setListeners() {
        binding.buttonSoma.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(applicationContext, "Soma selecionado", Toast.LENGTH_LONG).show()
        }

        binding.buttonApi.setOnClickListener {
            startActivity(Intent(this, ApiActivity::class.java))
            Toast.makeText(applicationContext, "Api selecionado", Toast.LENGTH_LONG).show()
        }
    }

}