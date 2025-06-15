package inha.primero_server.domain.barcode.dto.response;

import inha.primero_server.domain.barcode.entity.AuthLog;

public record AuthLogRes(String date, String time, String location, boolean success, String imagePath) {
    public static AuthLogRes from(AuthLog log) {
        return new AuthLogRes(
                log.getTimestamp().toLocalDate().toString(),
                log.getTimestamp().toLocalTime().toString(),
                log.getLocation(),
                log.isSuccess(),
                log.getImagePath()
        );
    }
}

