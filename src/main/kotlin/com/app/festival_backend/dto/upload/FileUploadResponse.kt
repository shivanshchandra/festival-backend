package com.app.festival_backend.dto.upload

data class FileUploadResponse(
    val fileName: String,
    val fileUrl: String,
    val contentType: String,
    val size: Long
)