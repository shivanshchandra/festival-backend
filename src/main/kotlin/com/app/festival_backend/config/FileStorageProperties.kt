package com.app.festival_backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.file")
class FileStorageProperties {
    var uploadDir: String = "uploads"
    var imageDir: String = "uploads/images"
    var videoDir: String = "uploads/videos"
    var baseUrl: String = "http://localhost:8080"
}