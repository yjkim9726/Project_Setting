package com.project.common.config.security.config

import com.project.common.config.security.filter.JwtAuthenticationFilter
import com.project.common.config.security.filter.JwtExceptionFilter
import com.project.common.config.security.provider.JwtTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * @fileName SecurityConfig
 * @author yunjeong
 * @since  2024/04/17
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2024/04/17        yunjeong        최초 작성
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig (
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtExceptionFilter: JwtExceptionFilter
){

    @Value("\${cors.allowed-origins}")
    private lateinit var origins: List<String>

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = origins
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")
        config.exposedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",config)

        return CorsFilter(source)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http.httpBasic { it.disable() }

        http.formLogin { it.disable() }

        http.csrf { it.disable() }

        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.cors { it.disable() }

        http.authorizeHttpRequests {
            it.requestMatchers("/api/v1/login/**").permitAll()
            it.anyRequest().authenticated()
        }

        // JwtAuthenticationFilter를 UserIdPasswordAuthenticationFilter 전에 넣는다. 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성
        http.addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        //  jwtExceptionFilter를 JwtAuthenticationFilter 전에 넣는다. 유저 인증 전, Exception 발생 종류에 따라 분기처리하여 Client에게 전달
        http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            // 정적 자원에 대한 Ignore 처리 자동으로
            it.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            it.ignoring().requestMatchers(
                "/v2/api-docs/**",
                "/configuration/**",
                "/v2/api-docs",
                "/swagger-ui/**/*",
                "/webjars/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/logout"
            )
        }
    }

}