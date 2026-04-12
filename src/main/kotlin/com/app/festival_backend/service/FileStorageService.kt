package com.app.festival_backend.service

import com.app.festival_backend.config.FileStorageProperties
import com.app.festival_backend.dto.upload.FileUploadResponse
import com.app.festival_backend.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.UUID

@Service
class FileStorageService(
    private val fileStorageProperties: FileStorageProperties
) {

    private val allowedImageTypes = setOf("image/jpeg", "image/png", "image/webp", "image/jpg")
    private val allowedVideoTypes = setOf("video/mp4", "video/quicktime", "video/x-msvideo", "video/x-matroska")

    fun uploadImage(file: MultipartFile): FileUploadResponse {
        validateFile(file, allowedImageTypes, "image")
        return storeFile(file, fileStorageProperties.imageDir, "images")
    }

    fun uploadVideo(file: MultipartFile): FileUploadResponse {
        validateFile(file, allowedVideoTypes, "video")
        return storeFile(file, fileStorageProperties.videoDir, "videos")
    }

    private fun validateFile(
        file: MultipartFile,
        allowedTypes: Set<String>,
        fileTypeLabel: String
    ) {
        if (file.isEmpty) {
            throw BadRequestException("Please select a $fileTypeLabel file to upload")
        }

        val contentType = file.contentType?.lowercase()
            ?: throw BadRequestException("Invalid $fileTypeLabel file type")

        if (contentType !in allowedTypes) {
            throw BadRequestException("Only valid $fileTypeLabel files are allowed")
        }
    }

    private fun storeFile(
        file: MultipartFile,
        targetDir: String,
        urlFolder: String
    ): FileUploadResponse {
        val originalFileName = StringUtils.cleanPath(file.originalFilename ?: "file")
        val extension = originalFileName.substringAfterLast('.', "")
            .lowercase()

        val generatedFileName = if (extension.isNotBlank()) {
            "${UUID.randomUUID()}.$extension"
        } else {
            UUID.randomUUID().toString()
        }

        val uploadPath: Path = Paths.get(targetDir).toAbsolutePath().normalize()
        Files.createDirectories(uploadPath)

        val targetLocation = uploadPath.resolve(generatedFileName)

        file.inputStream.use { inputStream ->
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        }

        val fileUrl = "${fileStorageProperties.baseUrl}/uploads/$urlFolder/$generatedFileName"

        return FileUploadResponse(
            fileName = generatedFileName,
            fileUrl = fileUrl,
            contentType = file.contentType ?: "application/octet-stream",
            size = file.size
        )
    }
}