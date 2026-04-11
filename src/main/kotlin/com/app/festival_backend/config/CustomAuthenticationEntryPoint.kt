package com.app.festival_backend.config

import com.app.festival_backend.dto.common.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val objectMapper = ObjectMapper()

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val apiResponse = ApiResponse<Nothing>(
            status = 401,
            message = "Invalid or missing authentication token",
            data = null
        )

        response.writer.write(objectMapper.writeValueAsString(apiResponse))
    }
}