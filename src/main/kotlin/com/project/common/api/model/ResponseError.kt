package com.project.common.api.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @fileName ResponseError
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
@Schema(description = "데이터 오류 모델")
data class ResponseError(
    @Schema(name = "status", description = "통신 상태값", example = "10000", required = true)
    val status: Int? = null,
    @Schema(name = "message", description = "메세지", example = "아이디가 없습니다.", required = true)
    val message: String? = null
)
