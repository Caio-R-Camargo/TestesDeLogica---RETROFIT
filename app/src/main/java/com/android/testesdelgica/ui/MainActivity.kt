package com.android.testesdelgica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.testesdelgica.Calculos
import com.android.testesdelgica.DevelopConstants
import com.android.testesdelgica.R
import com.android.testesdelgica.SharedPreferences
import com.android.testesdelgica.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mSecurityPreferences: SharedPreferences
    private val mCalculos = Calculos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSecurityPreferences = SharedPreferences(this)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        loadValues()

    }

    private fun loadValues() {
        if (mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_CALCULO) != "" && mSecurityPreferences.getString(
                DevelopConstants.KEY.VALOR_DESCONTO
            ) != ""
        ) {
            binding.lastUpdateCalculo.text =
                mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_CALCULO)
            binding.lastUpdateDesconto.text =
                mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_DESCONTO)
        }
    }

    private fun setListeners() {
        binding.buttonCalculo.setOnClickListener {

            val firstValue = binding.editText.text.toString()
            val secondValue = binding.editText2.text.toString()

            if (firstValue != "" && secondValue != "") {
                val result = mCalculos.soma(firstValue.toInt(), secondValue.toInt())
                binding.textResultado.visibility = View.VISIBLE
                binding.editDesconto.visibility = View.VISIBLE
                binding.buttoDesconto.visibility = View.VISIBLE
                binding.textPercent.visibility = View.VISIBLE
                binding.textResultado.text = result.toString()
                mSecurityPreferences.storeString(
                    DevelopConstants.KEY.VALOR_CALCULO,
                    result.toString()
                )
                loadValues()
            } else {
                Toast.makeText(applicationContext, R.string.erro_digite_numero, Toast.LENGTH_LONG)
                    .show()
            }

        }

        binding.buttoDesconto.setOnClickListener {

            val value = binding.editDesconto.text.toString()

            if (value != "") {
                val valorCalculo =
                    mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_CALCULO)
                val result = mCalculos.desconto(BigDecimal(value), BigDecimal(valorCalculo))
                val descontoAplicado = mCalculos.applyDesconto(valorCalculo.toInt(), result.toInt())
                binding.textDesconto.text = result.toString()
                mSecurityPreferences.storeString(
                    DevelopConstants.KEY.VALOR_DESCONTO,
                    result.toString()
                )
                binding.textDescontoAplicado.text = descontoAplicado.toString()
                binding.textDesconto.visibility = View.VISIBLE
                binding.textDescontoAplicado.visibility = View.VISIBLE
                binding.textDescontoAplicadoView.visibility = View.VISIBLE
                binding.textDescontoView.visibility = View.VISIBLE
                loadValues()

            } else {
                Toast.makeText(applicationContext, R.string.erro_digite_numero, Toast.LENGTH_LONG)
                    .show()
            }

        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, InitActivity::class.java))
            finish()
        }

    }

}
