package com.android.testesdelgica.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.android.testesdelgica.BuildConfig
import com.android.testesdelgica.DevelopConstants
import com.android.testesdelgica.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mSecurityPreferences: com.android.testesdelgica.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSecurityPreferences = com.android.testesdelgica.SharedPreferences(this)

        setListeners()
        darkModeValidation()
        loadInfo()
    }

    private fun darkModeValidation(){
        if (mSecurityPreferences.getBoolean(DevelopConstants.KEY.DARKMODE) == true){
            val toggle: Switch = binding.switch1
            toggle.isChecked = true
            binding.switch1.text = "desativar"
        }
    }

    private fun setListeners(){

        val toggle: Switch = binding.switch1
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                binding.switch1.text = "desativar"
                mSecurityPreferences.storeBoolean(DevelopConstants.KEY.DARKMODE, true)
            } else {
                mSecurityPreferences.storeBoolean(DevelopConstants.KEY.DARKMODE, false)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

    }

    private fun loadInfo(){
        binding.textPhoneModel.text = Build.MODEL
        binding.textManufacturer.text = Build.MANUFACTURER
        binding.textVersion.text = "build" + BuildConfig.VERSION_NAME
        binding.textUser.text = Build.USER
    }


}