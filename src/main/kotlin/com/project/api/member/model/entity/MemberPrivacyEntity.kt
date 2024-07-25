package com.project.api.member.model.entity

import com.project.common.api.model.enumer.Gender
import com.project.common.api.model.enumer.Nationality


/**
 * @fileName MemberPrivacyEntity
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
data class MemberPrivacyEntity(
    val memberId : Int?=null,
    val gender : Gender?= null,
    val nationality: Nationality?= null,
    val birth : String?= null,
    val age : Int?= null,
    val regDate: String?= null,
    val modDate: String?= null
)
