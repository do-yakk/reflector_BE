package com.doyak.reflector.payload.exception.handler;

import com.doyak.reflector.payload.code.BaseErrorCode;
import com.doyak.reflector.payload.exception.GeneralException;

public class UserHandler extends GeneralException {

    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}