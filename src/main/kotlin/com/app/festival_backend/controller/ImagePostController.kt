package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.CategoryContentRequest
import com.app.festival_backend.dto.common.DeleteImageRequest
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.dto.image.ImagePostUpdateRequest
import com.app.festival_backend.service.FileStorageService
import com.app.festival_backend.service.ImagePostService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
class ImagePostController(
    private val imagePostService: ImagePostService,
    private val fileStorageService: FileStorageService
) {

    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createJson(
        @Valid @RequestBody request: ImagePostRequest
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.create(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image created successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createMultipart(
        @RequestParam("title") title: String,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {

        val finalImageUrl = if (imageFile != null && !imageFile.isEmpty) {
            fileStorageService.uploadImage(imageFile).fileUrl
        } else {
            imageUrl.orEmpty()
        }

        val finalThumbnailUrl = if (thumbnailFile != null && !thumbnailFile.isEmpty) {
            fileStorageService.uploadImage(thumbnailFile).fileUrl
        } else {
            thumbnailUrl
        }

        val request = ImagePostRequest(
            title = title,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium ?: false,
            displayOrder = displayOrder
        )

        val response = imagePostService.create(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
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

    @PostMapping(value = ["/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getByIdJson(
        @Valid @RequestBody request: DeleteImageRequest
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.getById(request.imageId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getByIdMultipart(
        @Valid @ModelAttribute request: DeleteImageRequest
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.getById(request.imageId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/category/get"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getByCategoryJson(
        @Valid @RequestBody request: CategoryContentRequest,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/category/get"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getByCategoryMultipart(
        @Valid @ModelAttribute request: CategoryContentRequest,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getByCategoryIdPaginated(request.categoryId, page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
        @Valid @RequestBody request: ImagePostUpdateRequest
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @RequestParam("imageId") imageId: Long,
        @RequestParam("title", required = false) title: String?,
        @RequestParam("categoryId", required = false) categoryId: Long?,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {

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

        val request = ImagePostUpdateRequest(
            imageId = imageId,
            title = title,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = imagePostService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteJson(
        @Valid @RequestBody request: DeleteImageRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        imagePostService.delete(request.imageId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image deleted successfully",
                data = null
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun deleteMultipart(
        @Valid @ModelAttribute request: DeleteImageRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        imagePostService.delete(request.imageId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image deleted successfully",
                data = null
            )
        )
    }
}