package com.app.festival_backend.config

import com.app.festival_backend.dto.common.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {

    private val objectMapper = ObjectMapper()

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val apiResponse = ApiResponse<Nothing>(
            status = 403,
            message = "Access denied. You do not have permission to perform this action",
            data = null
        )

        response.writer.write(objectMapper.writeValueAsString(apiResponse))
    }
}