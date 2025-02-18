package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class CommunityHandler extends GeneralException {
    public CommunityHandler (BaseErrorCode code) {
        super(code);
    }
}
