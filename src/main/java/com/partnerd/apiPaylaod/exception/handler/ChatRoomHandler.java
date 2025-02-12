package com.partnerd.apiPaylaod.exception.handler;

import com.partnerd.apiPaylaod.code.BaseErrorCode;
import com.partnerd.apiPaylaod.exception.GeneralException;

public class ChatRoomHandler  extends GeneralException {

    public ChatRoomHandler(BaseErrorCode errorCode){super (errorCode); }
}

