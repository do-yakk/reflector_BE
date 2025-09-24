package com.doyak.reflector.payload.exception.handler;

import com.doyak.reflector.payload.code.BaseErrorCode;
import com.doyak.reflector.payload.exception.GeneralException;

public class BlockHandler extends GeneralException {

    public BlockHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}