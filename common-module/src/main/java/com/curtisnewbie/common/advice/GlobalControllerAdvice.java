package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.exceptions.MsgEmbeddedException;
import com.curtisnewbie.common.exceptions.UnrecoverableException;
import com.curtisnewbie.common.util.StructUtils;
import com.curtisnewbie.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.CompletionException;

import static java.lang.String.format;

/**
 * Base Controller Advice
 *
 * @author yongjie.zhuang
 */
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handleGeneralException(Exception e) {
        log.error("Unknown exception occurred", e);
        return Result.error("Unknown error, please try again later");
    }

    @ExceptionHandler(CompletionException.class)
    @ResponseBody
    public Result<?> handleCompletionException(CompletionException e) {
        log.error("Completion exception occurred", e);
        final Throwable c = e.getCause();
        if (c != null) {
            if (c instanceof MsgEmbeddedException)
                return handleMsgEmbeddedException((MsgEmbeddedException) c);
            if (c instanceof UnrecoverableException)
                return handleUnrecoverableMsgEmbeddedException((UnrecoverableException) c);
            if (c instanceof MethodArgumentNotValidException)
                return handleMethodArgumentNotValidException((MethodArgumentNotValidException) c);
        }
        return Result.error("Unknown error, please try again later");
    }

    @ExceptionHandler({MsgEmbeddedException.class})
    @ResponseBody
    public Result<?> handleMsgEmbeddedException(MsgEmbeddedException e) {
        log.info("MsgEmbeddedException thrown", e);
        String errorMsg = e.getMsg();
        if (!StringUtils.hasText(errorMsg)) {
            errorMsg = "Invalid request";
        }
        return Result.error(errorMsg);
    }

    @ExceptionHandler({UnrecoverableException.class})
    @ResponseBody
    public Result<?> handleUnrecoverableMsgEmbeddedException(UnrecoverableException e) {
        log.info("UnrecoverableException thrown", e);
        String errorMsg = e.getEmbeddedMsg();
        if (!StringUtils.hasText(errorMsg)) {
            errorMsg = "Invalid request";
        }
        return Result.error(errorMsg, e.getErrorCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("Validation failed", e);
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors.isEmpty()) {
            return Result.error("Invalid Argument");
        }

        // find the first error
        final FieldError fieldError = fieldErrors.get(0);
        return Result.error(format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()));
    }

}
