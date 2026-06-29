package com.arisman.nangdia.util

import java.util.*

object NumberFormatUtil {
    fun formatVotes(votes: Int?): String? {
        if (votes == null || votes <= 0) return null
        
        return when {
            votes >= 1_000_000 -> {
                String.format(Locale.US, "%.1fM", votes / 1_000_000.0)
            }
            votes >= 1_000 -> {
                String.format(Locale.US, "%.1fk", votes / 1_000.0)
            }
            else -> votes.toString()
        }
    }
    
    fun formatCurrency(amount: Long?): String? {
        if (amount == null || amount <= 0) return null
        val format = java.text.NumberFormat.getCurrencyInstance(Locale.US)
        format.maximumFractionDigits = 0
        return format.format(amount)
    }
}

