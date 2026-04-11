package com.app.festival_backend.controller

import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.service.VideoPostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class VideoPostController(
    private val videoPostService: VideoPostService
) {

    @PostMapping("/videos")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: VideoPostRequest): VideoPostResponse {
        return videoPostService.create(request)
    }

    @GetMapping("/videos")
    fun getAll(): List<VideoPostResponse> {
        return videoPostService.getAll()
    }

    @GetMapping("/videos/{id}")
    fun getById(@PathVariable id: Long): VideoPostResponse {
        return videoPostService.getById(id)
    }

    @GetMapping("/categories/{categoryId}/videos")
    fun getByCategoryId(@PathVariable categoryId: Long): List<VideoPostResponse> {
        return videoPostService.getByCategoryId(categoryId)
    }

    @PutMapping("/videos/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: VideoPostRequest
    ): VideoPostResponse {
        return videoPostService.update(id, request)
    }

    @DeleteMapping("/videos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        videoPostService.delete(id)
    }
}