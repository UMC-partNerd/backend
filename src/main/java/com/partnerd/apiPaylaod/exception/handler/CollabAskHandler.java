package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class CollabAskHandler extends GeneralException {
    public CollabAskHandler(BaseErrorCode code) {
        super(code);
    }
}

