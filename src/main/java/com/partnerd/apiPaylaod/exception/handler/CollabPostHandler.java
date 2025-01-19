package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseCode;
import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class CollabPostHandler extends GeneralException {
    public CollabPostHandler (BaseErrorCode code) {
        super(code);
    }
}
