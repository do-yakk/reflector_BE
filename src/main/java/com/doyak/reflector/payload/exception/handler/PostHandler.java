package com.doyak.reflector.payload.exception.handler;

import com.doyak.reflector.payload.code.BaseErrorCode;
import com.doyak.reflector.payload.exception.GeneralException;

public class PostHandler extends GeneralException {

    public PostHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}