package me.spring.auth.account.infrastructure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

class JWT(val issuer: String, val clientSecret: String, val expirySeconds: Int?) {

    private val algorithm: Algorithm = Algorithm.HMAC512(clientSecret)
    private val jwtVerifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    fun newToken(claims: Claims): String {
        val now = Date()
        val builder = JWT.create()
        builder.withIssuer(issuer)
        builder.withIssuedAt(now)
        if (expirySeconds!! > 0) {
            builder.withExpiresAt(Date(now.time + expirySeconds * 1000L))
        }
        builder.withClaim("userKey", claims.accountKey)
        builder.withClaim("name", claims.name)
        builder.withClaim("email", claims.email)
        builder.withArrayClaim("roles", claims.roles)
        return builder.sign(algorithm)
    }

    @Throws(JWTVerificationException::class)
    fun refreshToken(token: String): String {
        val claims = verify(token)
        claims.eraseIat()
        claims.eraseExp()
        return newToken(claims)
    }

    @Throws(JWTVerificationException::class)
    fun verify(token: String): Claims {
        return Claims(jwtVerifier.verify(token))
    }

    class Claims {
        var accountKey: Long ?= null
        var name: String ?= null
        var email: String ?= null
        var roles: Array<String> ?= null
        var iat: Date ?= null
        var exp: Date ?= null

        private constructor() {}
        constructor(decodedJWT: DecodedJWT) {
            val userKey = decodedJWT.getClaim("userKey")
            if (!userKey.isNull) this.accountKey = userKey.asLong()
            val name = decodedJWT.getClaim("name")
            if (!name.isNull) this.name = name.asString()
            val email = decodedJWT.getClaim("email")
            if (!email.isNull) this.email = email.asString()
            val roles = decodedJWT.getClaim("roles")
            if (!roles.isNull) this.roles = roles.asArray(String::class.java)
            iat = decodedJWT.issuedAt
            exp = decodedJWT.expiresAt
        }

        fun iat(): Long {
            return if (iat != null) iat!!.time else -1
        }

        fun exp(): Long {
            return if (exp != null) exp!!.time else -1
        }

        fun eraseIat() {
            iat = null
        }

        fun eraseExp() {
            exp = null
        }

        companion object {
            fun of(userKey: Long?, name: String?, email: String?, roles: Array<String>): Claims {
                val claims = Claims()
                claims.accountKey = userKey
                claims.name = name
                claims.email = email
                claims.roles = roles
                return claims
            }
        }
    }
}