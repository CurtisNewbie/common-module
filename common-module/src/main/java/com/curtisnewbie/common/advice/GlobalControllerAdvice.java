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
        log.error("Unknown Exception occurred", e);
        return Result.error("Unknown error, please try again later");
    }

    @ExceptionHandler({MsgEmbeddedException.class})
    @ResponseBody
    public Result<?> handleMsgEmbeddedException(MsgEmbeddedException e) {
        String errorMsg = e.getMsg();
        if (!StringUtils.hasText(errorMsg)) {
            errorMsg = "Invalid request";
        }
        return Result.error(errorMsg);
    }

    @ExceptionHandler({UnrecoverableException.class})
    @ResponseBody
    public Result<?> handleUnrecoverableMsgEmbeddedException(UnrecoverableException e) {
        String errorMsg = e.getEmbeddedMsg();
        if (!StringUtils.hasText(errorMsg)) {
            errorMsg = "Invalid request";
        }
        return Result.error(errorMsg, e.getErrorCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
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
