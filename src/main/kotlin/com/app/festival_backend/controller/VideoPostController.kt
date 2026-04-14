package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.service.FileStorageService
import com.app.festival_backend.service.VideoPostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/videos")
class VideoPostController(
    private val videoPostService: VideoPostService,
    private val fileStorageService: FileStorageService
) {

    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createJson(
        @Valid @RequestBody request: VideoPostRequest
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Video created successfully",
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
        @RequestParam("videoFile", required = false) videoFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("videoUrl", required = false) videoUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {

        val finalVideoUrl = videoFile?.let { fileStorageService.uploadVideo(it).fileUrl } ?: videoUrl.orEmpty()
        val finalThumbnailUrl = thumbnailFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: thumbnailUrl

        val request = VideoPostRequest(
            title = title,
            videoUrl = finalVideoUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = videoPostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Video created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<VideoPostResponse>>> {
        val response = videoPostService.getAllPaginated(page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Videos fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/get/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
        @PathVariable id: Long,
        @Valid @RequestBody request: VideoPostRequest
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video updated successfully",
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
        @RequestParam("videoFile", required = false) videoFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("videoUrl", required = false) videoUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {

        val finalVideoUrl = videoFile?.let { fileStorageService.uploadVideo(it).fileUrl } ?: videoUrl.orEmpty()
        val finalThumbnailUrl = thumbnailFile?.let { fileStorageService.uploadImage(it).fileUrl } ?: thumbnailUrl

        val request = VideoPostRequest(
            title = title,
            videoUrl = finalVideoUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = videoPostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video updated successfully",
                data = response
            )
        )
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        videoPostService.delete(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video deleted successfully",
                data = null
            )
        )
    }
}