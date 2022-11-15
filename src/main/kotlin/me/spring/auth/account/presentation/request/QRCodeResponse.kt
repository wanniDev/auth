package me.spring.auth.account.presentation.request

import java.time.LocalDateTime

data class QRCodeResponse(val imageBytes: ByteArray, val createDateTime: LocalDateTime) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QRCodeResponse

        if (!imageBytes.contentEquals(other.imageBytes)) return false
        if (createDateTime != other.createDateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageBytes.contentHashCode()
        result = 31 * result + createDateTime.hashCode()
        return result
    }
}
