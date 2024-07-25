package com.project.api.member.service

import com.project.api.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @fileName MemberService
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
@Service
class MemberService @Autowired constructor(
    private val memberRepository: MemberRepository
){


}