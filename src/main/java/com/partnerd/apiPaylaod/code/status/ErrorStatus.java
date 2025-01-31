package com.partnerd.apiPaylaod.code.status;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

  
  
  
  
  
    //로그인(갱신)
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN4001", "유효하지 않은 JWT 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.FORBIDDEN, "TOKEN4002", "리프레시 토큰이 만료되었습니다. 다시 로그인하세요."),

    // 로그인(인증) 관련
    AUTH_INVALID_CODE(HttpStatus.BAD_REQUEST, "AUTH4001", "유효하지 않은 인가 코드입니다."),
    AUTH_FAILED_TOKEN_RETRIEVAL(HttpStatus.BAD_REQUEST, "AUTH4002", "토큰 조회에 실패했습니다."),
    AUTH_FAILED_USER_INFO(HttpStatus.BAD_REQUEST, "AUTH4003", "사용자 정보 조회에 실패했습니다."),

    // 회원가입 관련
    REGISTER_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "REGISTER4001", "잘못된 요청 데이터입니다."),
    REGISTER_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "REGISTER4002", "사용자를 찾을 수 없습니다."),
    REGISTER_AGREEMENTS_NOT_FOUND(HttpStatus.NOT_FOUND, "REGISTER4003", "약관 정보를 찾을 수 없습니다."),

    // 멤버 관련 (임의로 추가한 것이라서 충돌나면 지워도 무방합니다!)
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "해당 사용자가 없습니다."),
  
    // 마이페이지 내프로필 관련
    MYPAGE_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "MYPROFILE4001", "해당 사용자가 없습니다."),
    AGREEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MYPROFILE4002", "해당 약관이 없습니다."),

    // 마이페이지 퍼스널페이지 관련
    PERSONAL_NOT_FOUND(HttpStatus.NOT_FOUND, "MYPROFILE4001", "해당 사용자의 퍼스널페이지가 없습니다."),


    // 프로젝트 홍보 관련
    PROMOTION_PROJECT_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "PROMOTIONPROJECT4001", "존재하지 않는 홍보 프로젝트id입니다."),
    PROMOTION_PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROMOTIONPROJECT4002", "프로젝트 홍보글이 없습니다."),

    // 프로젝트 모집 관련
    RECRUIT_PROJECT_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "RECRUITPROJECT4001", "존재하지 않는 모집 프로젝트id입니다."),
    RECRUIT_PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "RECRUITPROJECT4002", "프로젝트 모집글이 없습니다."),
    RECRUIT_PROJECT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "RECRUITPROJECT4003", "존재하지 않는 댓글입니다."),
    RECRUIT_PARENT_PROJECT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "RECRUITPROJECT4003", "댓글달 댓글이 없습니다."),

    // 콜라보레이션 글 관련
    COLLAB_POST_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COLLABPOST4000", "필수 항목을 모두 입력해주세요."),
    COLLAB_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABPOST4040", "콜라보레이션 포스트를 찾을 수 없습니다."),
    COLLAB_POST_ALREADY_EXIST(HttpStatus.CONFLICT, "COLLABPOST4090", "이미 존재하는 콜라보레이션 포스트입니다."),
    COLLAB_POST_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "COLLABPOST4030", "동아리 리더만 콜라보 글을 작성할 수 있습니다."),
    COLLAB_POST_CLUB_MEMBERSHIP_REQUIRED(HttpStatus.FORBIDDEN, "COLLABPOST4031", "동아리에 가입하지 않은 사용자는 글을 작성할 수 없습니다."),
    COLLAB_POST_NOT_AUTHOR(HttpStatus.FORBIDDEN, "COLLABPOST4032", "글 작성자만 이 작업을 수행할 수 있습니다."),
    COLLAB_POST_NOT_VALID(HttpStatus.BAD_REQUEST, "COLLABPOST4001", "유효하지 않은 요청입니다. 요청 데이터를 확인해주세요."),


    // 콜라보레이션 문의글 관련
    COLLAB_INQUIRY_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABINQUIRY4001", "해당 콜라보레이션 문의글이 없습니다."),
    COLLAB_INQUIRY_PARENT_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABINQUIRY4001", "답변할 콜라보레이션 문의글이 없습니다."),
    CANNOT_REMOVE_LIKE_BELOW_ZERO(HttpStatus.FORBIDDEN, "COLLABINQUIRY403", "금지된 요청입니다."),
    COLLAB_INQUIRY_NOT_AUTHOR(HttpStatus.FORBIDDEN, "COLLABINQUIRY4003", "문의글 작성자만 수행할 수 있습니다."),
    COLLAB_INQUIRY_CHILD_NOT_AUTHOR(HttpStatus.FORBIDDEN, "COLLABINQUIRY4003", "답변 작성자만 수행할 수 있습니다."),

    // 콜라보 요청 관련
    COLLAB_ASK_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABASK4001", "해당 콜라보레이션 요청이 없습니다."),
    COLLAB_ASK_ALREADY_EXIST(HttpStatus.NOT_FOUND, "COLLABASK4002", "이미 요청한 콜라보레이션입니다."),
    COLLAB_ASK_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "COLLABASK4003", "작성자는 자신의 글에 요청할 수 없습니다."),

    // 동아리
    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB4001","해당 동아리가 없습니다."),

    //카테고리
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY4001","해당 카테고리가 없습니다."),

    // 행사 유형 관련
    EVENT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENTTYPE4001", "이벤트 타입이 없습니다."),

    // 동아리 멤버 관련
    CLUB_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUBMEMBER4001", "클럽 멤버가 없습니다."),
    CLUB_MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "CLUBMEMBER4002", "이미 존재하는 클럽 멤버입니다."),
    CLUB_MEMBER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "CLUBMEMBER4003", "권한이 없습니다."),
    CLUB_MEMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "CLUBMEMBER4004", "유효하지 않은 클럽 멤버입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}