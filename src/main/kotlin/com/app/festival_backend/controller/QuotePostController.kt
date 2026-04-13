package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.service.QuotePostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class QuotePostController(
    private val quotePostService: QuotePostService
) {

    @PostMapping("/quotes")
    fun create(@Valid @RequestBody request: QuotePostRequest): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Quote created successfully",
                data = response
            )
        )
    }

    @GetMapping("/quotes")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getAllPaginated(page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quotes fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/quotes/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/categories/{categoryId}/quotes")
    fun getByCategoryId(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getByCategoryIdPaginated(categoryId, page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quotes fetched successfully",
                data = response
            )
        )
    }

    @PostMapping("/quotes/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: QuotePostRequest
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote updated successfully",
                data = response
            )
        )
    }

    @DeleteMapping("/quotes/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> {
        quotePostService.delete(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote deleted successfully",
                data = null
            )
        )
    }
}