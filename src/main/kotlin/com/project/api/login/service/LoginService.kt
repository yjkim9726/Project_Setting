package com.project.api.login.service

import com.project.api.login.repository.LoginRepository
import com.project.common.config.logger.LoggerFactory
import com.project.common.config.security.provider.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

/**
 * @fileName LoginService
 * @author yunjeong
 * @since  2024/04/19
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2024/04/19        yunjeong        최초 작성
 */
@Service
class LoginService @Autowired constructor(
    private val loginRepository: LoginRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    companion object : LoggerFactory()

    /**
     * 테스트 jwt 토큰 발급 로직
     * @param login Login
     * @return ReJwtToken
     */
    fun testJWTToken(login: com.project.api.login.model.request.Login): com.project.api.login.model.response.ReJwtToken {
        val authenticationToken = UsernamePasswordAuthenticationToken(login.id, login.pw)
        val authentication: Authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val token = jwtTokenProvider.generateToken(authentication)

        return com.project.api.login.model.response.ReJwtToken(
            memberId = 1,
            grantType = token.grantType,
            accessToken = token.accessToken,
            refreshToken = token.refreshToken,
            authority = token.authority
        )
    }
}