package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class CategoryHandler extends GeneralException {

    public CategoryHandler(BaseErrorCode errorCode){super (errorCode); }
}
