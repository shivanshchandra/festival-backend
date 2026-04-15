package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.CategoryContentRequest
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.service.ImagePostService
import com.app.festival_backend.service.QuotePostService
import com.app.festival_backend.service.VideoPostService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryContentController(
    private val imagePostService: ImagePostService,
    private val videoPostService: VideoPostService,
    private val quotePostService: QuotePostService
) {

    @PostMapping(value = ["/images/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getImagesByCategoryIdJson(
        @Valid @RequestBody request: CategoryContentRequest,
        @RequestHeader(name = "page", defaultValue = "0") page: Int,
        @RequestHeader(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(ApiResponse(200, "Images fetched successfully", response))
    }

    @PostMapping(value = ["/images/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getImagesByCategoryIdMultipart(
        @Valid @ModelAttribute request: CategoryContentRequest,
        @RequestHeader(name = "page", defaultValue = "0") page: Int,
        @RequestHeader(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(ApiResponse(200, "Images fetched successfully", response))
    }

    @PostMapping(value = ["/videos/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getVideosByCategoryIdJson(
        @Valid @RequestBody request: CategoryContentRequest,
        @RequestHeader(name = "page", defaultValue = "0") page: Int,
        @RequestHeader(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<VideoPostResponse>>> {
        val response = videoPostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(ApiResponse(200, "Videos fetched successfully", response))
    }

    @PostMapping(value = ["/videos/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getVideosByCategoryIdMultipart(
        @Valid @ModelAttribute request: CategoryContentRequest,
        @RequestHeader(name = "page", defaultValue = "0") page: Int,
        @RequestHeader(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<VideoPostResponse>>> {
        val response = videoPostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(ApiResponse(200, "Videos fetched successfully", response))
    }

    @PostMapping(value = ["/quotes/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getQuotesByCategoryIdJson(
        @Valid @RequestBody request: CategoryContentRequest,
        @RequestHeader(name = "page", defaultValue = "0") page: Int,
        @RequestHeader(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(ApiResponse(200, "Quotes fetched successfully", response))
    }

    @PostMapping(value = ["/quotes/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getQuotesByCategoryIdMultipart(
        @Valid @ModelAttribute request: CategoryContentRequest,
        @RequestHeader(name = "page", defaultValue = "0") page: Int,
        @RequestHeader(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<QuotePostResponse>>> {
        val response = quotePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(ApiResponse(200, "Quotes fetched successfully", response))
    }
}