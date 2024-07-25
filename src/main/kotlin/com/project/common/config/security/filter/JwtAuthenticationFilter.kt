package com.project.common.config.security.filter

import com.project.common.config.logger.LoggerFactory
import com.project.common.config.security.provider.JwtTokenProvider
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean


/**
 * @fileName JwtAuthenticationFilter
 * @author yunjeong
 * @since  2023/10/19
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/10/19        yunjeong        최초 작성
 */
@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    companion object: LoggerFactory()

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        val httpServletResponse = response as HttpServletResponse
        val path = httpServletRequest.servletPath
        // log.info("통신 요청 URL ::::::>>>>> $path")
        val token = jwtTokenProvider.resolveToken((request as HttpServletRequest?)!!)
        // 유효한 토큰인지 확인
        if (!path.startsWith("/api/v1") || shouldBypassAuthentication(path)){
            log.info("JWT 토큰 검사 PASS URL :::::: $path")
            chain.doFilter(request, httpServletResponse)
        } else if (jwtTokenProvider.validateJwtToken(token) && token != null) {
            val authentication: Authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
                chain.doFilter(request, httpServletResponse)
        }
    }

    private fun shouldBypassAuthentication(requestURI: String): Boolean {
        // 토큰 인증을 무시할 URL 패턴 등록
        val bypassUrls = listOf(
            "/api/v1/login/.*"
        )
        return bypassUrls.any { requestURI.matches(it.toRegex()) }
    }

}