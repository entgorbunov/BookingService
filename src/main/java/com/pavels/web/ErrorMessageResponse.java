package com.pavels.web;

import java.time.LocalDateTime;

public record ErrorMessageResponse(
    String message, String detailedMessage, LocalDateTime dateTime
) {
}
