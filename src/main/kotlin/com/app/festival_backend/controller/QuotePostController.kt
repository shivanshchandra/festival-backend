package com.app.festival_backend.controller

import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.service.QuotePostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class QuotePostController(
    private val quotePostService: QuotePostService
) {

    @PostMapping("/quotes")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: QuotePostRequest): QuotePostResponse {
        return quotePostService.create(request)
    }

    @GetMapping("/quotes")
    fun getAll(): List<QuotePostResponse> {
        return quotePostService.getAll()
    }

    @GetMapping("/quotes/{id}")
    fun getById(@PathVariable id: Long): QuotePostResponse {
        return quotePostService.getById(id)
    }

    @GetMapping("/categories/{categoryId}/quotes")
    fun getByCategoryId(@PathVariable categoryId: Long): List<QuotePostResponse> {
        return quotePostService.getByCategoryId(categoryId)
    }

    @PutMapping("/quotes/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: QuotePostRequest
    ): QuotePostResponse {
        return quotePostService.update(id, request)
    }

    @DeleteMapping("/quotes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        quotePostService.delete(id)
    }
}