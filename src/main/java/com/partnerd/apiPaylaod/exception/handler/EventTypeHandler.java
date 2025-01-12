package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class EventTypeHandler extends GeneralException {
    public EventTypeHandler(BaseErrorCode code) {
        super(code);
    }
}
