package com.arisman.nangdia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arisman.nangdia.domain.model.WatchlistMedia

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: String,
    val mediaId: String,
    val title: String,
    val poster: String?,
    val mediaType: String,
    val provider: String,
    val year: Int?,
    val score: Int?,
    val released: String?,
    val releasedDigital: String?,
    val addedAt: Long
) {
    fun toWatchlistMedia(): WatchlistMedia {
        return WatchlistMedia(
            id = id,
            mediaId = mediaId,
            title = title,
            poster = poster,
            mediaType = mediaType,
            provider = provider,
            year = year,
            score = score,
            released = released,
            releasedDigital = releasedDigital,
            addedAt = addedAt
        )
    }

    companion object {
        fun fromWatchlistMedia(media: WatchlistMedia): WatchlistEntity {
            return WatchlistEntity(
                id = media.id,
                mediaId = media.mediaId,
                title = media.title,
                poster = media.poster,
                mediaType = media.mediaType,
                provider = media.provider,
                year = media.year,
                score = media.score,
                released = media.released,
                releasedDigital = media.releasedDigital,
                addedAt = media.addedAt
            )
        }
    }
}

