package com.project.api.login.model.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * @fileName Login
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
@Schema(description = "테스트용 로그인")
data class Login(
    @field:NotBlank(message = "id를 입력해주세요.")
    @field:Size(min = 5, max = 10, message = "id는 5자리 이상 15자리 이하로 입력해야합니다.")
    @Schema(name = "id", description = "로그인 아이디", example = "emotiv01", required = true)
    val id: String?,
    @Schema(name = "pw", description = "로그인 비밀번호", example = "qwe123", required = true)
    val pw: String?="qwe123"
)
