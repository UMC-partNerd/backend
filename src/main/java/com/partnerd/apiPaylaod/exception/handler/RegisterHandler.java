package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class RegisterHandler extends GeneralException {
    public RegisterHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
