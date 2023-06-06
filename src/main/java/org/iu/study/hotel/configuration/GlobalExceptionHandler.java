package org.iu.study.hotel.configuration;

import lombok.RequiredArgsConstructor;
import org.iu.study.hotel.exceptions.DatabaseEntityNotFoundException;
import org.iu.study.hotel.utilities.NotificationMessages;
import org.iu.study.hotel.service.NotificationService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final NotificationService notificationService;

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatchError() {
        return notificationService.returnSimpleError(NotificationMessages.INVALID_URL_PARAM_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DatabaseEntityNotFoundException.class)
    public ModelAndView handleInvalidEntityIdError(@NotNull DatabaseEntityNotFoundException exception) {
        logger.warn("User specified invalid entity id " + exception.getEntityId() +
                " while trying to access a " + exception.getEntityName());

        return notificationService.returnSimpleError(NotificationMessages.ENTITY_NOT_FOUND_ERROR);
    }
}
