package com.project.api.member.repository

import com.project.api.member.model.entity.MemberEntity
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

/**
 * @fileName MemberRepository
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
@Mapper
@Repository
interface MemberRepository {

    fun findUserByMemberEntity(memberEntity: MemberEntity): MemberEntity?
}