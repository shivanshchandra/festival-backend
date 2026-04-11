package com.app.festival_backend.controller

import com.app.festival_backend.dto.auth.AuthUserData
import com.app.festival_backend.dto.auth.LoginRequest
import com.app.festival_backend.dto.auth.RegisterRequest
import com.app.festival_backend.dto.auth.UpdateUserRequest
import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request: RegisterRequest
    ): ResponseEntity<ApiResponse<AuthUserData>> {
        val response = authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "User registered successfully",
                data = response
            )
        )
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<ApiResponse<AuthUserData>> {
        val response = authService.login(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Login successful",
                data = response
            )
        )
    }

    @PostMapping("/update")
    fun updateUser(
        @Valid @RequestBody request: UpdateUserRequest
    ): ResponseEntity<ApiResponse<AuthUserData>> {
        val response = authService.updateUser(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "User updated successfully",
                data = response
            )
        )
    }
}