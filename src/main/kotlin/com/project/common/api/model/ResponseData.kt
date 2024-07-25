package com.project.common.api.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @fileName ResponseData
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
@Schema(description = "데이터 응답 모델")
data class ResponseData<T> (
    @Schema(name = "status", description = "통신 상태값", example = "200", required = true)
    val status: Int,
    @Schema(name = "data", description = "객체", example = "", required = true)
    val data: T
)