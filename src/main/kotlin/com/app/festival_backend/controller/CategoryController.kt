package com.app.festival_backend.controller

import com.app.festival_backend.dto.category.CategoryRequest
import com.app.festival_backend.dto.category.CategoryResponse
import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.service.CategoryService
import com.app.festival_backend.service.FileStorageService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService,
    private val fileStorageService: FileStorageService
) {

    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createJson(
        @Valid @RequestBody request: CategoryRequest
    ): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Category created successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createMultipart(
        @RequestParam("name") name: String,
        @RequestParam("description", required = false) description: String?,
        @RequestParam("isPremium", defaultValue = "false") isPremium: Boolean,
        @RequestParam("displayOrder", defaultValue = "0") displayOrder: Int,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<CategoryResponse>> {

        val finalImageUrl = imageFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: imageUrl
        val finalThumbnailUrl = thumbnailFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: thumbnailUrl

        val request = CategoryRequest(
            name = name,
            description = description,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = categoryService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Category created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>> {
        val response = categoryService.getAllPaginated(page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Categories fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/get/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
        @PathVariable id: Long,
        @Valid @RequestBody request: CategoryRequest
    ): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @PathVariable id: Long,
        @RequestParam("name") name: String,
        @RequestParam("description", required = false) description: String?,
        @RequestParam("isPremium", defaultValue = "false") isPremium: Boolean,
        @RequestParam("displayOrder", defaultValue = "0") displayOrder: Int,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<CategoryResponse>> {

        val finalImageUrl = imageFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: imageUrl
        val finalThumbnailUrl = thumbnailFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: thumbnailUrl

        val request = CategoryRequest(
            name = name,
            description = description,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = categoryService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category updated successfully",
                data = response
            )
        )
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.delete(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category deleted successfully",
                data = null
            )
        )
    }
}