package com.arisman.nangdia.util

object PopularityFormatUtil {
    fun formatPopularityLabel(popularity: Double?): String? {
        if (popularity == null || popularity <= 0) return null
        
        return when {
            popularity > 1000 -> "Viral / Blockbuster"
            popularity > 500 -> "Sangat Populer"
            popularity > 150 -> "Sedang Tren"
            popularity > 50 -> "Populer"
            popularity > 20 -> "Cukup Aktif"
            else -> "Niche / Kurang Aktif"
        }
    }
}
