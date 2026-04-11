package com.app.festival_backend.controller

import com.app.festival_backend.dto.category.CategoryRequest
import com.app.festival_backend.dto.category.CategoryResponse
import com.app.festival_backend.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CategoryRequest): CategoryResponse {
        return categoryService.create(request)
    }

    @GetMapping
    fun getAll(): List<CategoryResponse> {
        return categoryService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CategoryResponse {
        return categoryService.getById(id)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: CategoryRequest
    ): CategoryResponse {
        return categoryService.update(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        categoryService.delete(id)
    }
}