package com.app.festival_backend.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        "my-super-secret-key-my-super-secret-key".toByteArray()
    )

    private val expirationTime = 1000L * 60 * 60 * 24

    fun generateToken(email: String): String {
        return Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact()
    }

    fun extractEmail(token: String): String {
        return extractAllClaims(token).subject
    }

    fun isTokenValid(token: String, email: String): Boolean {
        val extractedEmail = extractEmail(token)
        return extractedEmail == email && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}