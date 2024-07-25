package com.project.common.config.security.provider

import com.project.common.api.model.ResponseError
import com.project.common.config.exception.ErrorCode
import com.project.common.config.exception.ErrorCode.JWT_CODE
import com.project.common.config.exception.ErrorCode.NO_AUTHORITY
import com.project.common.config.exception.ErrorException
import com.project.common.config.logger.LoggerFactory
import com.project.common.config.security.model.JwtTokenVo
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.SignatureException
import java.util.*
import java.util.stream.Collectors

/**
 * @fileName JwtTokenProvider
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
class JwtTokenProvider (
    @Value("\${spring.security.jwt.secret-key}")
    private val secretKey: String,

    @Value("\${spring.security.jwt.algorithm}")
    private val algorithm: SignatureAlgorithm,

    @Value("\${spring.security.jwt.access-token-expired-time}")
    private val accessTokenExpiredTime: String,

    @Value("\${spring.security.jwt.refresh-token-expired-time}")
    private val refreshTokenExpiredTime: String,

    @Value("\${spring.security.jwt.grant-type}")
    private val grantType: String
){
    companion object: LoggerFactory()

    private var key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(authentication: Authentication): JwtTokenVo {
        // 권한 체크
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        val now = Date().time

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .setHeaderParam("typ","JWT")
            .claim("auth",authorities)
            .setIssuedAt(Date())
            .setExpiration(Date(now + Integer.parseInt(accessTokenExpiredTime))) // set Expire Time 2시간
            .signWith(key, algorithm) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 셋팅
            .compact()

        val refreshToken = Jwts.builder()
            .setExpiration(Date(now + Integer.parseInt(refreshTokenExpiredTime))) // set Expire Time 2주
            .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 셋팅
            .compact()

        return JwtTokenVo(
            grantType = grantType,
            accessToken = accessToken,
            refreshToken = refreshToken,
            authority = authorities
        )
    }

    // 토큰 정보가 유효한지 확인하는 메소드
    fun validateJwtToken(token: String?): Boolean {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        } catch (e: SignatureException) {
            log.debug("${ErrorCode.WRONG_TYPE_TOKEN} >> ${e.message}")
            throw JwtException(ErrorCode.WRONG_TYPE_TOKEN)
        } catch (e: MalformedJwtException) {
            log.debug("${ErrorCode.UNSUPPORTED_TOKEN} >> ${e.message}")
            throw JwtException(ErrorCode.UNSUPPORTED_TOKEN)
        } catch (e: ExpiredJwtException) {
            log.debug("${ErrorCode.EXPIRED_TOKEN} >> ${e.message}")
            throw JwtException(ErrorCode.EXPIRED_TOKEN)
        } catch (e: IllegalArgumentException) {
            log.debug("${ErrorCode.NOT_EXIST_TOKEN} >> ${e.message}")
            throw JwtException(ErrorCode.NOT_EXIST_TOKEN)
        }
    }

    // Request Header 에서 토큰 정보 추출
    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken)) {
            bearerToken.substring(7)
        } else null
    }

    // 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    fun getAuthentication(accessToken: String) : Authentication {

        // 토큰 복호화
        val claims : Claims = parseClaims(accessToken)

        if ( claims["auth"] == null ) {
            throw ErrorException(ResponseError(status= JWT_CODE, message = NO_AUTHORITY))
        }

        // 클레임에서 권한 정보 가져오기
        val authorities = claims["auth"].toString().split(",").map{ SimpleGrantedAuthority(it) }.toList()

        // UserDetails 객체를 만들어서 Authentication 리턴
        val principal : UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }


}

