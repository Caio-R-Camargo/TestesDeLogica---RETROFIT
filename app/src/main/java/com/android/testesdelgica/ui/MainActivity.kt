package com.android.testesdelgica.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.testesdelgica.Calculos
import com.android.testesdelgica.DevelopConstants
import com.android.testesdelgica.R
import com.android.testesdelgica.SharedPreferences
import com.android.testesdelgica.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.*
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
        if (mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_CALCULO) != "" && mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_DESCONTO) != "") {
            binding.buttonGeratePdf.visibility = View.VISIBLE
        }
        if(mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_CALCULO) != ""){
            binding.lastUpdateCalculo.text = mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_CALCULO)
        }
        if(mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_DESCONTO) != ""){
            binding.lastUpdateDesconto.text = mSecurityPreferences.getString(DevelopConstants.KEY.VALOR_DESCONTO)
        }

    }

    private fun setVisibilitySoma(){
        binding.textResultado.visibility = View.VISIBLE
        binding.editDesconto.visibility = View.VISIBLE
        binding.buttoDesconto.visibility = View.VISIBLE
        binding.textPercent.visibility = View.VISIBLE
    }

    private fun setVisibilityDesconto(){
        binding.textDesconto.visibility = View.VISIBLE
        binding.textDescontoAplicado.visibility = View.VISIBLE
        binding.textDescontoAplicadoView.visibility = View.VISIBLE
        binding.textDescontoView.visibility = View.VISIBLE
        binding.buttonGeratePdf.visibility = View.VISIBLE
        binding.viewLastLine.visibility = View.VISIBLE
    }

    private fun setListeners() {
        binding.buttonCalculo.setOnClickListener {

            val firstValue = binding.editText.text.toString()
            val secondValue = binding.editText2.text.toString()

            if (firstValue != "" && secondValue != "") {
                val result = mCalculos.soma(firstValue.toInt(), secondValue.toInt())
                mSecurityPreferences.storeString(DevelopConstants.KEY.VALOR_CALCULO, result.toString())
                setVisibilitySoma()
                binding.textResultado.text = result.toString()
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
                setVisibilityDesconto()
                loadValues()

            } else {
                Toast.makeText(applicationContext, R.string.erro_digite_numero, Toast.LENGTH_LONG)
                    .show()
            }

        }

        binding.buttonGeratePdf.setOnClickListener {
            val documentPDF = PdfDocument()

            val pageInfo = PageInfo.Builder(500, 600, 1).create()

            val newPage = documentPDF.startPage(pageInfo)

            val canvas = newPage.canvas

            val colorText = Paint()
            colorText.color = Color.BLUE


            canvas.drawText(getString(R.string.historico_calculo), 100F, 100F, colorText)
            canvas.drawText(binding.lastUpdateCalculo.text.toString(), 200F, 100F, colorText)
            canvas.drawText(getString(R.string.historico_desconto), 100F, 120F, colorText)
            canvas.drawText(binding.lastUpdateDesconto.text.toString(), 200F, 120F, colorText)
            documentPDF.finishPage(newPage)

            val targetPdf = "/storage/emulated/0/Download/Extrato.pdf"
            val file = File(targetPdf)

            try {
                documentPDF.writeTo(FileOutputStream(file))
                Toast.makeText(applicationContext, targetPdf, Toast.LENGTH_LONG).show()
            } catch (e: IOException){
                Snackbar.make(findViewById(android.R.id.content), e.toString(),
                    Snackbar.LENGTH_INDEFINITE).setAction("Action", null).setTextColor(
                    resources.getColor(R.color.white)).setAction("Ok", View.OnClickListener {}).setActionTextColor(
                    resources.getColor(R.color.white)).show()
            }

            documentPDF.close()

        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, InitActivity::class.java))
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_settings_day){
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }



}
