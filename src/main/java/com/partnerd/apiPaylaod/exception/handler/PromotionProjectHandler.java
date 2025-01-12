package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class PromotionProjectHandler extends GeneralException {
    public PromotionProjectHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
