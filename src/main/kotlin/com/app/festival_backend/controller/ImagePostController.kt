package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.service.FileStorageService
import com.app.festival_backend.service.ImagePostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Image created successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createMultipart(
        @RequestParam("title") title: String,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("isPremium", defaultValue = "false") isPremium: Boolean,
        @RequestParam("displayOrder", defaultValue = "0") displayOrder: Int,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {

        val finalImageUrl = imageFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: imageUrl.orEmpty()
        val finalThumbnailUrl = thumbnailFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: thumbnailUrl

        val request = ImagePostRequest(
            title = title,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = imagePostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Image created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<ImagePostResponse>>> {
        val response = imagePostService.getAllPaginated(page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Images fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/get/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {
        val response = imagePostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
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

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @PathVariable id: Long,
        @RequestParam("title") title: String,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("isPremium", defaultValue = "false") isPremium: Boolean,
        @RequestParam("displayOrder", defaultValue = "0") displayOrder: Int,
        @RequestParam("imageFile", required = false) imageFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("imageUrl", required = false) imageUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<ImagePostResponse>> {

        val finalImageUrl = imageFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: imageUrl.orEmpty()
        val finalThumbnailUrl = thumbnailFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: thumbnailUrl

        val request = ImagePostRequest(
            title = title,
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = imagePostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Image updated successfully",
                data = response
            )
        )
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
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