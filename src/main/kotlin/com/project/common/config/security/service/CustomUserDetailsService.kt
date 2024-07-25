package com.project.common.config.security.service

import com.project.api.member.model.entity.MemberEntity
import com.project.api.member.repository.MemberRepository
import com.project.common.api.model.ResponseError
import com.project.common.config.exception.ErrorCode
import com.project.common.config.exception.ErrorException
import com.project.common.config.logger.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * @fileName CustomUserDetailsService
 * @author kimkihyeon
 * @since 2023/06/05
 * @version 1.0
 *
 * @Modification Information
 * @
 * @    DATE           AUTHOR          NOTE
 * @ -----------     ----------    -------------------
 * @ 2023/06/05     kimkihyeon       최초 작성
 */
@Service
class CustomUserDetailsService @Autowired constructor(
    private val memberRepository: MemberRepository,
): UserDetailsService {

    companion object: LoggerFactory()

    override fun loadUserByUsername(uuid : String): UserDetails {
        val memberEntity = MemberEntity(
            uuid = uuid
        )
        val member = memberRepository.findUserByMemberEntity(memberEntity) ?: throw ErrorException(ResponseError(
            ErrorCode.MEMBER_01_CODE, ErrorCode.MEMBER_01_MSG))
        return User(member.uuid,"",getAuthorities("member"))
    }

    private fun getAuthorities(role: String): Collection<GrantedAuthority> {
        val grantedAuthorityList: MutableList<GrantedAuthority> = ArrayList()
        val arr = role.split(",")
        for (i: Int in arr.indices) {
            grantedAuthorityList.add(SimpleGrantedAuthority(arr[i]))
        }
        return grantedAuthorityList
    }

}