package com.android.testesdelgica

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager

class FingerprintHelper {

    companion object{

        fun authenticationAvailable(context: Context): Boolean {

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
                return false
            }

            val biometricManager : BiometricManager = BiometricManager.from(context)

            when( biometricManager.canAuthenticate()){
                BiometricManager.BIOMETRIC_SUCCESS -> return true
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> return false
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> return false
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> return false
            }
            return false
        }

    }

}