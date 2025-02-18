package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class CommunityCommentHandler extends GeneralException {
    public CommunityCommentHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
