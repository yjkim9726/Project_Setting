package com.project.api.member.model.entity

import com.project.common.api.model.enumer.AppPlatform
import com.project.common.api.model.enumer.SocialPlatform
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @fileName MemberEntity
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
@Schema(description = "사용자 테이블")
data class MemberEntity (
    @Schema(name= "memberId", description = "사용자 일련번호", example = "1", type = "Int")
    val memberId : Int?=null,
    @Schema(name= "uuid", description = "사용자 고유 아이디값", example = "ejwiroewij.fewfjiowe", type = "String")
    val uuid : String?=null,
    @Schema(name= "uid", description = "사용자 소셜 고유값", example = "1000000", type = "Int")
    val uid : Int?=null,
    @Schema(name= "appPlatform", description = "사용자 스토어", example = "")
    val appPlatform : AppPlatform?=null,
    @Schema(name= "socialPlatform", description = "소셜로그인 플랫폼", example = "")
    val socialPlatform : SocialPlatform?=null,
    @Schema(name= "auth", description = "권한", example = "member")
    val auth : String?=null,
    @Schema(name= "languageCode", description = "사용자 사용 언어", example = "ko-KR")
    val languageCode : String?= null,
    @Schema(name= "expirationDate", description = "이용권 만료일", example = "")
    val expirationDate : String?= null,
    @Schema(name= "useYn", description = "사용여부", example = "true, false", type = "Boolean")
    val useYn : Boolean?= null,
    @Schema(name= "quitReason", description = "탈퇴 이유", example = "1,2,3,4,5")
    val quitReason : Int?= null,
    @Schema(name= "regDate", description = "가입 날짜", example = "")
    val regDate : String?= null
)