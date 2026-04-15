package com.app.festival_backend.controller

import com.app.festival_backend.dto.category.CategoryRequest
import com.app.festival_backend.dto.category.CategoryResponse
import com.app.festival_backend.dto.category.CategoryUpdateRequest
import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.DeleteCategoryRequest
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.service.CategoryService
import com.app.festival_backend.service.FileStorageService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import com.app.festival_backend.exception.BadRequestException

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
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category created successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createMultipart(
        @RequestParam("name") name: String,
        @RequestParam("description", required = false) description: String?,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<CategoryResponse>> {

        val finalImageUrl = if (imageFile != null && !imageFile.isEmpty) {
            fileStorageService.uploadImage(imageFile).fileUrl
        } else {
            imageUrl?.trim()
        }

        val finalThumbnailUrl = if (thumbnailFile != null && !thumbnailFile.isEmpty) {
            fileStorageService.uploadImage(thumbnailFile).fileUrl
        } else {
            thumbnailUrl?.trim()
        }

        if (finalImageUrl.isNullOrBlank()) {
            throw BadRequestException("Image file is required")
        }

        if (finalThumbnailUrl.isNullOrBlank()) {
            throw BadRequestException("Thumbnail file is required")
        }

        val request = CategoryRequest(
            name = name,
            description = description,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            isPremium = isPremium ?: false,
            displayOrder = displayOrder
        )

        val response = categoryService.create(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>> {
        val response = categoryService.getAllPaginated(page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Categories fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getByIdJson(
        @Valid @RequestBody request: DeleteCategoryRequest
    ): ResponseEntity<ApiResponse<CategoryResponse>> {

        val response = categoryService.getById(request.categoryId)

        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category fetched successfully",
                data = response
            )
        )
    }


    @PostMapping(value = ["/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getByIdMultipart(
        @Valid @ModelAttribute request: DeleteCategoryRequest
    ): ResponseEntity<ApiResponse<CategoryResponse>> {

        val response = categoryService.getById(request.categoryId)

        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
        @Valid @RequestBody request: CategoryUpdateRequest
    ): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("name", required = false) name: String?,
        @RequestParam("description", required = false) description: String?,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<CategoryResponse>> {

        val finalImageUrl = if (imageFile != null && !imageFile.isEmpty) {
            fileStorageService.uploadImage(imageFile).fileUrl
        } else {
            imageUrl
        }

        val finalThumbnailUrl = if (thumbnailFile != null && !thumbnailFile.isEmpty) {
            fileStorageService.uploadImage(thumbnailFile).fileUrl
        } else {
            thumbnailUrl
        }

        val request = CategoryUpdateRequest(
            categoryId = categoryId,
            name = name,
            description = description,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = categoryService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteJson(
        @Valid @RequestBody request: DeleteCategoryRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.delete(request.categoryId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category deleted successfully",
                data = null
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun deleteMultipart(
        @Valid @ModelAttribute request: DeleteCategoryRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.delete(request.categoryId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category deleted successfully",
                data = null
            )
        )
    }
}