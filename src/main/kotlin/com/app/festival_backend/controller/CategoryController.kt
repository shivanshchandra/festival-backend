package com.app.festival_backend.controller

import com.app.festival_backend.dto.category.CategoryRequest
import com.app.festival_backend.dto.category.CategoryResponse
import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @PostMapping
    fun create(@Valid @RequestBody request: CategoryRequest): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Category created successfully",
                data = response
            )
        )
    }

    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<CategoryResponse>>> {
        val response = categoryService.getAll()
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Categories fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/paginated")
    fun getAllPaginated(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>> {
        val response = categoryService.getAllPaginated(page, size)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Categories fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category fetched successfully",
                data = response
            )
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: CategoryRequest
    ): ResponseEntity<ApiResponse<CategoryResponse>> {
        val response = categoryService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category updated successfully",
                data = response
            )
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.delete(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Category deleted successfully",
                data = null
            )
        )
    }
}