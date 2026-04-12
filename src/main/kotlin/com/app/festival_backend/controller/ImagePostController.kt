package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.service.ImagePostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ImagePostController(
    private val imagePostService: ImagePostService
) {

    @PostMapping("/images")
    fun create(@Valid @RequestBody request: ImagePostRequest): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Image created successfully",
                data = response
            )
        )
    }

    @GetMapping("/images")
    fun getAll(): ResponseEntity<ApiResponse<List<ImagePostResponse>>> {
        val response = imagePostService.getAll()
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/images/paginated")
    fun getAllPaginated(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getAllPaginated(page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/images/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/categories/{categoryId}/images")
    fun getByCategoryId(@PathVariable categoryId: Long): ResponseEntity<ApiResponse<List<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryId(categoryId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/categories/{categoryId}/images/paginated")
    fun getByCategoryIdPaginated(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryIdPaginated(categoryId, page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @PutMapping("/images/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: ImagePostRequest
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image updated successfully",
                data = response
            )
        )
    }

    @DeleteMapping("/images/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> {
        imagePostService.delete(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image deleted successfully",
                data = null
            )
        )
    }
}