package com.app.festival_backend.exception

class ResourceNotFoundException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)

class AlreadyExistsException(message: String) : RuntimeException(message)

class UnauthorizedException(message: String) : RuntimeException(message)

class ForbiddenException(message: String) : RuntimeException(message)