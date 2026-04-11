package com.app.festival_backend.service

import com.app.festival_backend.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found") }

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        )
    }
}