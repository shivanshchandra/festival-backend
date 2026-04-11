package com.app.festival_backend.controller

import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.service.ImagePostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ImagePostController(
    private val imagePostService: ImagePostService
) {

    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: ImagePostRequest): ImagePostResponse {
        return imagePostService.create(request)
    }

    @GetMapping("/images")
    fun getAll(): List<ImagePostResponse> {
        return imagePostService.getAll()
    }

    @GetMapping("/images/{id}")
    fun getById(@PathVariable id: Long): ImagePostResponse {
        return imagePostService.getById(id)
    }

    @GetMapping("/categories/{categoryId}/images")
    fun getByCategoryId(@PathVariable categoryId: Long): List<ImagePostResponse> {
        return imagePostService.getByCategoryId(categoryId)
    }

    @PutMapping("/images/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: ImagePostRequest
    ): ImagePostResponse {
        return imagePostService.update(id, request)
    }

    @DeleteMapping("/images/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        imagePostService.delete(id)
    }
}