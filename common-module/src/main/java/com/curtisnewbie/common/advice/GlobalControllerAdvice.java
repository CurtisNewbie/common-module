package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.exceptions.MsgEmbeddedException;
import com.curtisnewbie.common.exceptions.UnrecoverableMsgEmbeddedException;
import com.curtisnewbie.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

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

    @ExceptionHandler({UnrecoverableMsgEmbeddedException.class})
    @ResponseBody
    public Result<?> handleUnrecoverableMsgEmbeddedException(UnrecoverableMsgEmbeddedException e) {
        log.error("Unrecoverable exception occurred", e);
        String errorMsg = e.getEmbeddedMsg();
        if (!StringUtils.hasText(errorMsg)) {
            errorMsg = "Invalid request";
        }
        return Result.error(errorMsg);
    }

}
