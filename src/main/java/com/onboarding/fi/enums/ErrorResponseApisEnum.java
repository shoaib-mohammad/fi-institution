package com.onboarding.fi.enums;

import org.springframework.http.HttpStatus;

public enum ErrorResponseApisEnum {

    ServerError(500, HttpStatus.INTERNAL_SERVER_ERROR, "failed.key", "server.error.key"),
    ItemNotFound(601, HttpStatus.NOT_FOUND, "not.found.key", "not.found.item.key"),
    MissingParameter(602, HttpStatus.BAD_REQUEST, "missing.params.key", "missing.params.key"),
    BadRequest(400, HttpStatus.BAD_REQUEST, "failed.key", "bad.request.key"),
    Unauthorized(401, HttpStatus.UNAUTHORIZED, "invalid.token.key", "invalid.token.key"),
    InvalidUser(404, HttpStatus.NOT_FOUND, "invalid.user.key", "invalid.user.key"),
    AccessDenied(403, HttpStatus.FORBIDDEN, "access.denied.key", "access.denied.key");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String title;
    private final String message;

    ErrorResponseApisEnum(final int errorCode, HttpStatus httpStatus, final String title, final String message) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.title = title;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}