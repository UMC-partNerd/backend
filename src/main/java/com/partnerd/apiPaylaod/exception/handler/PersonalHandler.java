package com.partnerd.apiPaylaod.exception.handler;


import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class PersonalHandler extends GeneralException {

    public PersonalHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}