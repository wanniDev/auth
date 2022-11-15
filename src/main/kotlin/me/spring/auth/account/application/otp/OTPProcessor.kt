package me.spring.auth.account.application.otp

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import de.taimos.totp.TOTP
import me.spring.auth.account.application.AccountRepositoryAdapter
import me.spring.auth.account.application.AccountTokenRepositoryAdapter
import me.spring.auth.account.domain.AccountToken
import me.spring.auth.account.infrastructure.security.AuthInfo
import org.apache.commons.codec.binary.Base32
import org.apache.commons.codec.binary.Hex
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.SecureRandom

@Component
class OTPProcessor(
                   private val accountRepository: AccountRepositoryAdapter,
                   private val accountTokenRepository: AccountTokenRepositoryAdapter
) {
    fun supplyQRCode(authInfo: AuthInfo): ByteArray {
        val secret: String
        val accountId = authInfo.id
        if (accountTokenRepository.existByAccountId(accountId)) {
            secret = accountTokenRepository.findTokenByAccountId(accountId)
        } else {
            secret = generateNewSecret()
            val account = accountRepository.findById(accountId)
            accountTokenRepository.save(AccountToken(account, secret))
        }

        val barCodeUrl = generateAuthBarCode(secret, issuer = "localhost", email = authInfo.email)
        return generateQRImage(barCodeUrl, 240, 240)
    }

    private fun generateQRImage(barCodeUrl: String, width: Int, height: Int): ByteArray {
        val matrix = MultiFormatWriter().encode(barCodeUrl, BarcodeFormat.QR_CODE, width, height)
        val bos = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(matrix, "png", bos)
        return bos.toByteArray()
    }

    private fun getTOTPCode(secretKey: String?): String? {
        val base32 = Base32()
        val bytes = base32.decode(secretKey)
        return TOTP.getOTP(Hex.encodeHexString(bytes))
    }

    private fun generateAuthBarCode(secret: String, issuer: String, email: String?): String {
        val url = URLEncoder.encode("$issuer:$email", StandardCharsets.UTF_8).replace("+", "%20")
        val encodedSecret = URLEncoder.encode(secret, StandardCharsets.UTF_8).replace("+", "%20")
        val encodedIssuer = URLEncoder.encode(issuer, StandardCharsets.UTF_8).replace("+", "%20")
        return "otpauth://totp/$url?secret=$encodedSecret&issuer=$encodedIssuer"
    }

    private fun generateNewSecret(): String {
        val random = SecureRandom()

        val bytes = ByteArray(size = 20)
        random.nextBytes(bytes)

        val base32 = Base32()
        return base32.encodeAsString(bytes)
    }
}