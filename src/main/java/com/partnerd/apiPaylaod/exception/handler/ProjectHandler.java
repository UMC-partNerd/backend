package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class ProjectHandler extends GeneralException {
    public ProjectHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
