package org.iu.study.hotel.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseEntityNotFoundException extends RuntimeException {
    @Getter
    private final String entityName;
    @Getter
    private final long entityId;
}
