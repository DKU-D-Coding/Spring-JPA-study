package com.dku.springstudy.auth.exception;

import com.dku.springstudy.error.ApplicationException;
import com.dku.springstudy.error.ErrorCode;

public class TokenException extends ApplicationException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
