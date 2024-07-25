package com.project.api.login.model.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @fileName JwtTokenVo
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
@Schema(description = "jwt 토큰 반환 모델")
data class ReJwtToken(
    @Schema(name = "사용자 일련번호", example = "1")
    var memberId: Int? = null,
    @Schema(name = "토큰 타입", example = "Bearer")
    val grantType: String? = null,
    @Schema(name = "엑세스 토큰", example = "")
    val accessToken: String? = null,
    @Schema(name = "리프레시 토큰", example = "")
    val refreshToken: String? = null,
    @Schema(name = "사용자 권한", example = "member")
    var authority: String? = null
)
