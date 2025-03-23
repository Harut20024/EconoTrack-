package com.banking.BankPredict.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * @author Alexander Klyakhandler <alexander.klyakhandler@bostongene.com>
 */
@Getter
public class ResponseCodeException extends RuntimeException
{

    private final HttpStatusCode httpStatusCode;

    private final String statusText;

    public ResponseCodeException(HttpStatusCode httpStatusCode, String statusText)
    {
        this.httpStatusCode = httpStatusCode;
        this.statusText = statusText;
    }
}
