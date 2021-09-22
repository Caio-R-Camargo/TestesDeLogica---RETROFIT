package com.android.testesdelgica

import java.math.BigDecimal

class Calculos {

    fun soma(valor: Int, valorDois: Int): Int {
        return valor + valorDois
    }

    fun desconto(valorDesconto: BigDecimal, valorProduto: BigDecimal): BigDecimal{
        val div = valorDesconto.divide(BigDecimal(100))
        return  div * valorProduto
    }

    fun applyDesconto(valorDesconto: Int, valorProduto: Int): Int {
        return valorDesconto - valorProduto
    }

}