package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class ClubMembershipRequestHandler extends GeneralException {
    public ClubMembershipRequestHandler(BaseErrorCode code) {
        super(code);
    }
}

