package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class ClubHandler extends GeneralException {

    public ClubHandler(BaseErrorCode errorCode){super (errorCode); }
}
