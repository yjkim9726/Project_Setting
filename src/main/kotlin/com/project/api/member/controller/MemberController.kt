package com.project.api.member.controller

import com.project.api.member.service.MemberService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @fileName MemberController
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
@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/v1/member")
class MemberController @Autowired constructor(
    private val memberService : MemberService
){

    @GetMapping("/healthyCheck")
    fun createTestMember(): String {
        return "hello loadBalancer"
    }
}