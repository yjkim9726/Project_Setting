package com.project.common.config.exception

import com.project.common.api.model.ResponseError
import com.project.common.config.logger.LoggerFactory
import org.apache.ibatis.javassist.NotFoundException
import org.mybatis.spring.MyBatisSystemException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.NullPointerException
import java.sql.SQLException

/**
 * @fileName ExceptionHandler
 * @author kimkihyeon
 * @since 2023/05/09
 * @version 1.0
 *
 * @Modification Information
 * @
 * @    DATE           AUTHOR          NOTE
 * @ -----------     ----------    -------------------
 * @ 2023/05/09     kimkihyeon       최초 작성
 */
@RestControllerAdvice
class ExceptionHandler{

    companion object : LoggerFactory()

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ErrorException::class)
    protected fun handleBaseException(e: ErrorException): ResponseEntity<Any> {
        log.debug("[오류] 코드 : ${e.errorCode.status} , 메세지 : ${e.errorCode.message}")
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseError(e.errorCode.status, e.errorCode.message))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Any> {
        log.error("MethodArgumentNotValidException :: ${e.printStackTrace()}")
        var errorMessage = ""
        e.bindingResult.allErrors.forEach { i ->
            errorMessage += i.defaultMessage + ", "
        }
        log.debug("[오류] 코드 : 400(MethodArgumentNotValidException) , 메세지 : $errorMessage")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseError(HttpStatus.BAD_REQUEST.value(), errorMessage.substring(0 until errorMessage.length -2 ) ))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<Any> {
        log.error("MissingServletRequestParameterException :: ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseError(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."))
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<*> {
        log.error("RuntimeException:: ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.OK).body(ResponseError(HttpStatus.BAD_REQUEST.value(), e.message ?: "알 수 없는 오류가 발생하였습니다."))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<Any> {
        log.error("HttpMessageNotReadableException :: ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.OK).body(ResponseError(status = 443, message = "허용되지 않는 값이 요청되었습니다."))
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SecurityException::class)
    fun securityExceptionHandler(e: SecurityException): ResponseEntity<Any> {
        log.error("SecurityExceptionHandler :::: ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ResponseError(HttpStatus.FORBIDDEN.value(), e.message!!))
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(e: NotFoundException): ResponseEntity<Any> {
        log.error("NotFoundException = ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ResponseError(HttpStatus.NOT_FOUND.value(), "해당 리소스 정보를 찾을 수 없습니다."))
    }

    @ExceptionHandler(SQLException::class)
    fun sqlExceptionHandler(e: SQLException): ResponseEntity<Any> {
        log.error("SQL Exception = ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터 베이스 SQL을 확인해 보시기 바랍니다."))
    }

    @ExceptionHandler(MyBatisSystemException::class)
    fun mybatisExceptionHandler(e: MyBatisSystemException): ResponseEntity<Any> {
        log.error("Mybatis System Exception = ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버의 마이바티스 쿼리가 동작하지 않습니다."))
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerExceptionHandler(e: Error): ResponseEntity<Any> {
        log.error("Null System Exception = ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "null 은 허용되지 않습니다."))
    }

    @ExceptionHandler(SessionAuthenticationException::class)
    fun sessionAuthenticationExceptionHandler(e: Error): ResponseEntity<Any> {
        log.error("sessionAuthentication Exception Exception = ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미 로그인된 계정이 존재합니다. 로그아웃됩니다."))
    }

    @ExceptionHandler(Exception::class)
    fun errorExceptionHandler(e: Error): ResponseEntity<Any> {
        log.error("Error System Exception = ${e.printStackTrace()}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 서버 오류가 발생했습니다."))
    }

}