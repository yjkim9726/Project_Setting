package com.project.common.config.security.model

/**
 * @fileName JwtTokenVo
 * @author yunjeong
 * @since  2023/05/08
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR        e NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/05/08        yunjeong        최초 작성
 */
data class JwtTokenVo(
//    @ApiModelProperty(value = "사용자 일련번호", example = "사용자 일련번호")
    var memberId: Int? = null,
//    @ApiModelProperty(value = "토큰 타입", example = "토큰 타입")
    val grantType: String? = null,
//    @ApiModelProperty(value = "엑세스 토큰", example = "엑세스 토큰")
    val accessToken: String? = null,
//    @ApiModelProperty(value = "리프레시 토큰", example = "리프레시 토큰")
    val refreshToken: String? = null,
//    @ApiModelProperty(value = "사용자 권한", example = "사용자 권한")
    var authority: String? = null
)
