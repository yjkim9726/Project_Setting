package com.project.common.api.controller

import com.project.common.api.model.ResponseData
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * @fileName ResponseController
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
open class ResponseController {

    private fun <T> wrapResponseEntity(data: T): ResponseData<T> {
        return ResponseData(status = HttpStatus.OK.value(), data = data)
    }

    fun <T> getSuccessResponseEntity(data: T): ResponseEntity<ResponseData<T>> {
        return ResponseEntity.ok(wrapResponseEntity(data))
    }
}