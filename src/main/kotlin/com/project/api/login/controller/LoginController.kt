package com.project.api.login.controller

import com.project.api.login.service.LoginService
import com.project.common.api.controller.ResponseController
import com.project.common.api.model.ResponseData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @fileName LoginController
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
@Tag(name = "로그인", description = "로그인 관련 API")
@RestController
@RequestMapping("/api/v1/login")
class LoginController @Autowired constructor(
    private val loginService : LoginService
) : ResponseController() {

    @Tag(name = "로그인")
    @Operation(summary = "로드 밸런스 연결 상태 확인 API", description = "서버의 작동 여부 확인을 위해 헬스 체크를 조회한다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = [Content(schema = Schema(implementation = ResponseData::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Not Found"),
    )
//    @Parameters(
//        Parameter(name = "email", description = "이메일", example = "chrome123@naver.com"),
//        Parameter(name = "password", description = "6자~12자 이내", example = "abcd1234"),
//        Parameter(name = "companyName", description = "업체명", example = "코리아 시스템"),
//        Parameter(name = "companyNumber", description = "업체 번호", example = "112233"),
//        Parameter(name = "companyAddress", description = "업체 주소", example = "인천시 미추홀구 용현동")
//    )
    @GetMapping("/healthyCheck")
    fun createTestMember(): String {
        return "hello loadBalancer"
    }


    @Tag(name = "로그인")
    @Operation(summary = "Jwt 토큰 테스트", description = "Jwt 토큰 로그인을 테스트 하기 위한 Api")
    @PostMapping("/test")
    fun testJWTToken(@Validated @RequestBody login: com.project.api.login.model.request.Login): ResponseEntity<ResponseData<com.project.api.login.model.response.ReJwtToken>> {
        val jwtToken = loginService.testJWTToken(login)
        return getSuccessResponseEntity(jwtToken)
    }


}