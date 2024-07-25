package com.project.common.config.exception

import com.project.common.api.model.ResponseError

/**
 * @fileName RestApiException
 * @author yunjeong
 * @since  2023/05/11
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/05/11        yunjeong        최초 작성
 */

class ErrorException(responseError: ResponseError) : RuntimeException() {
    val errorCode: ResponseError = responseError
}
