package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.CategoryContentRequest
import com.app.festival_backend.dto.common.DeleteQuoteRequest
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.dto.quote.QuotePostUpdateRequest
import com.app.festival_backend.service.QuotePostService
import jakarta.validation.Valid
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
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
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
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {

        val request = QuotePostRequest(
            title = title,
            quoteText = quoteText,
            categoryId = categoryId,
            isPremium = isPremium ?: false,
            displayOrder = displayOrder
        )

        val response = quotePostService.create(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getAllPaginated(page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quotes fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getByIdJson(
        @Valid @RequestBody request: DeleteQuoteRequest
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.getById(request.quoteId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getByIdMultipart(
        @Valid @ModelAttribute request: DeleteQuoteRequest
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.getById(request.quoteId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/category/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getByCategoryJson(
        @Valid @RequestBody request: CategoryContentRequest,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quotes fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/category/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getByCategoryMultipart(
        @Valid @ModelAttribute request: CategoryContentRequest,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quotes fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
        @Valid @RequestBody request: QuotePostUpdateRequest
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {
        val response = quotePostService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @RequestParam("quoteId") quoteId: Long,
        @RequestParam("title", required = false) title: String?,
        @RequestParam("quoteText", required = false) quoteText: String?,
        @RequestParam("categoryId", required = false) categoryId: Long?,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?
    ): ResponseEntity<ApiResponse<QuotePostResponse>> {

        val request = QuotePostUpdateRequest(
            quoteId = quoteId,
            title = title,
            quoteText = quoteText,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = quotePostService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteJson(
        @Valid @RequestBody request: DeleteQuoteRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        quotePostService.delete(request.quoteId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote deleted successfully",
                data = null
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun deleteMultipart(
        @Valid @ModelAttribute request: DeleteQuoteRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        quotePostService.delete(request.quoteId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Quote deleted successfully",
                data = null
            )
        )
    }
}