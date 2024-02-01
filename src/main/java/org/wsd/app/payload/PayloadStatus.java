package org.wsd.app.payload;

public enum PayloadStatus {
    SUCCESS(200), ERROR(500), BAD_REQUEST(400), UNAUTHORIZED(401), FORBIDDEN(403), NOT_FOUND(404), CONFLICT(409);
    private final int code;

    PayloadStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}