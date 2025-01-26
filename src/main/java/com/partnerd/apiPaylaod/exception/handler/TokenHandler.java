package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class TokenHandler extends GeneralException {
    public TokenHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
