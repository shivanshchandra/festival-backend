package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.upload.FileUploadResponse
import com.app.festival_backend.service.FileStorageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/upload")
class UploadController(
    private val fileStorageService: FileStorageService
) {

    @PostMapping("/image")
    fun uploadImage(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<ApiResponse<FileUploadResponse>> {
        val response = fileStorageService.uploadImage(file)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Image uploaded successfully",
                data = response
            )
        )
    }

    @PostMapping("/video")
    fun uploadVideo(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<ApiResponse<FileUploadResponse>> {
        val response = fileStorageService.uploadVideo(file)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Video uploaded successfully",
                data = response
            )
        )
    }
}