package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.service.ImagePostService
import com.app.festival_backend.service.QuotePostService
import com.app.festival_backend.service.VideoPostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryContentController(
    private val imagePostService: ImagePostService,
    private val videoPostService: VideoPostService,
    private val quotePostService: QuotePostService
) {

    @GetMapping("/{categoryId}/images/get")
    fun getImagesByCategoryId(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryIdPaginated(categoryId, page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/{categoryId}/videos/get")
    fun getVideosByCategoryId(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<VideoPostResponse>>> {
        val response = videoPostService.getByCategoryIdPaginated(categoryId, page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Videos fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/{categoryId}/quotes/get")
    fun getQuotesByCategoryId(
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
}