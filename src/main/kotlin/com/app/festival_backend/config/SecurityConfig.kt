package com.app.festival_backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling {
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
                it.accessDeniedHandler(customAccessDeniedHandler)
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/api/auth/**").permitAll()

                auth.requestMatchers(HttpMethod.GET, "/api/**")
                    .hasAnyRole("USER", "ADMIN")

                auth.requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")

                auth.requestMatchers(HttpMethod.POST, "/api/images/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.PUT, "/api/images/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.DELETE, "/api/images/**").hasRole("ADMIN")

                auth.requestMatchers(HttpMethod.POST, "/api/videos/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.PUT, "/api/videos/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasRole("ADMIN")

                auth.requestMatchers(HttpMethod.POST, "/api/quotes/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.PUT, "/api/quotes/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.DELETE, "/api/quotes/**").hasRole("ADMIN")

                auth.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}