package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.service.QuotePostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quotes")
class QuotePostController(
    private val quotePostService: QuotePostService
) {

    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createJson(
        @Valid @RequestBody request: QuotePostRequest
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Quote created successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createMultipart(
        @RequestParam("title") title: String,
        @RequestParam("quoteText") quoteText: String,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("isPremium", defaultValue = "false") isPremium: Boolean,
        @RequestParam("displayOrder", defaultValue = "0") displayOrder: Int
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {

        val request = QuotePostRequest(
            title = title,
            quoteText = quoteText,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = quotePostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Quote created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
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

    @GetMapping("/get/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
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

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @PathVariable id: Long,
        @RequestParam("title") title: String,
        @RequestParam("quoteText") quoteText: String,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("isPremium", defaultValue = "false") isPremium: Boolean,
        @RequestParam("displayOrder", defaultValue = "0") displayOrder: Int
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {

        val request = QuotePostRequest(
            title = title,
            quoteText = quoteText,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = quotePostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote updated successfully",
                data = response
            )
        )
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
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