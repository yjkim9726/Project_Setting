package com.project.common.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.common.api.model.ResponseError
import com.project.common.config.exception.ErrorCode
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

/**
 * @fileName JwtExceptionFilter
 * @author yunjeong
 * @since  2023/10/20
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/10/20        yunjeong        최초 작성
 */
@Component
class JwtExceptionFilter (
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: JwtException) {
            val message: String? = ex.message
            if (ErrorCode.NOT_EXIST_TOKEN == message) {
                setResponse(response, ErrorCode.NOT_EXIST_TOKEN)
            } else if (ErrorCode.WRONG_TYPE_TOKEN == message) {
                setResponse(response, ErrorCode.WRONG_TYPE_TOKEN)
            } else if (ErrorCode.EXPIRED_TOKEN == message) {
                setResponse(response, ErrorCode.EXPIRED_TOKEN)
            } else if (ErrorCode.UNSUPPORTED_TOKEN == message) {
                setResponse(response, ErrorCode.UNSUPPORTED_TOKEN)
            } else if (ErrorCode.CONCURRENT_USER == message) {
                setResponse(response, ErrorCode.CONCURRENT_USER)
            }
            else {
                setResponse(response, ErrorCode.ACCESS_DENIED)
            }
        }
    }

    @Throws(RuntimeException::class, IOException::class)
    private fun setResponse(response: HttpServletResponse, errorMessage: String) {
        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "utf-8"
        response.status = HttpStatus.OK.value()
        var responseError = ResponseError()
        responseError = if (errorMessage == ErrorCode.CONCURRENT_USER) {
            ResponseError(
                status = HttpStatus.UNAUTHORIZED.value(),
                message = errorMessage
            )
        } else {
            ResponseError(
                status = HttpStatus.UNAUTHORIZED.value(),
                message = errorMessage
            )
        }
        val responseBody = objectMapper.writeValueAsString(responseError)
        response.writer.print(responseBody)
    }
}