package com.app.festival_backend.exception

import com.app.festival_backend.dto.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.multipart.MultipartException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse(
                status = 404,
                message = ex.message ?: "Resource not found",
                data = null
            )
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                status = 400,
                message = ex.message ?: "Bad request",
                data = null
            )
        )
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExists(ex: AlreadyExistsException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                status = 409,
                message = ex.message ?: "Resource already exists",
                data = null
            )
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiResponse(
                status = 401,
                message = ex.message ?: "Unauthorized access",
                data = null
            )
        )
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(ex: ForbiddenException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ApiResponse(
                status = 403,
                message = ex.message ?: "Access denied",
                data = null
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String?>>> {
        val errors = ex.bindingResult.fieldErrors.associate { fieldError: FieldError ->
            fieldError.field to (fieldError.defaultMessage ?: "Invalid value")
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                status = 400,
                message = "Validation failed",
                data = errors
            )
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                status = 400,
                message = "Invalid value for parameter: ${ex.name}",
                data = null
            )
        )
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceeded(ex: MaxUploadSizeExceededException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                status = 400,
                message = "File size exceeds the maximum allowed limit",
                data = null
            )
        )
    }

    @ExceptionHandler(MultipartException::class)
    fun handleMultipartException(ex: MultipartException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                status = 400,
                message = "Invalid multipart request",
                data = null
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneral(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse(
                status = 500,
                message = "Something went wrong",
                data = null
            )
        )
    }
}