package com.project.common.config.exception

/**
 * @fileName Error
 * @author kimkihyeon
 * @since 2023/05/09
 * @version 1.0
 *
 * @Modification Information
 * @
 * @    DATE           AUTHOR          NOTE
 * @ -----------     ----------    -------------------
 * @ 2023/05/09     kimkihyeon       최초 작성
 */

object ErrorCode {

    // 네이밍 규칙
    // 1. 대문자로 작성한다.
    // 2. 명사+숫자+CODE , 명사+숫자+MSG
    // 3. 하나의 세트 코트와 메시지는 동일한 명사+숫자를 사용한다.
    const val TEST_01_CODE = 123
    const val TEST_01_MSG = "테스트"

    // member
    const val MEMBER_01_CODE = 10000
    const val MEMBER_01_MSG = "사용자를 찾을 수 없습니다."


    // jwt 토큰 에러 (코드값은 30000 으로 통일)
    const val JWT_CODE = 30000
    const val NO_AUTHORITY = "권한이 존재하지 않는 토큰입니다."
    const val NOT_EXIST_TOKEN = "인증 토큰이 존재하지 않습니다."
    const val WRONG_TYPE_TOKEN = "잘못된 토큰 정보입니다."
    const val EXPIRED_TOKEN = "만료된 토큰 입니다. 다시 로그인 해주세요."
    const val UNSUPPORTED_TOKEN = "지원하지 않는 토큰 방식입니다."
    const val ACCESS_DENIED = "알 수 없는 이유로 요청이 거절되었습니다."
    const val CONCURRENT_USER = "동시 접속된 사용자입니다."
}
