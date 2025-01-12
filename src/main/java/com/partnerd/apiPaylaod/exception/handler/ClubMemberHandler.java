package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class ClubMemberHandler extends GeneralException {
    public ClubMemberHandler(BaseErrorCode code) {
        super(code);
    }
}
