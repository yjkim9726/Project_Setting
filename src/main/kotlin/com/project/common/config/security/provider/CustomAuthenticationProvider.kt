package com.project.common.config.security.provider

import com.project.common.config.exception.ErrorException
import com.project.common.config.logger.LoggerFactory
import com.project.common.config.security.service.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

/**
 * @fileName CustomAuthenticationProvider
 * @author yunjeong
 * @since  2023/04/17
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/04/17        yunjeong        최초 작성
 */
@Component
class CustomAuthenticationProvider(
    val customUserDetailsService: CustomUserDetailsService
) : AuthenticationProvider {

    companion object: LoggerFactory()

    @Throws(ErrorException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val uuid = authentication.name
        val userInfo = customUserDetailsService.loadUserByUsername(uuid)

        return UsernamePasswordAuthenticationToken(userInfo,"",userInfo.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}