package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;


/**
 * Advice that log the controller's method execution
 * <p>
 * This is very useful for debugging, as you can read the actual arguments received and objects returned.
 * <p>
 *
 * @author yongjie.zhuang
 */
@Slf4j
@Aspect
public class ControllerConsoleLogAdvice {

    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut() && execution(* *(..))")
    public Object methodCall(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        Object result = null;
        try {
            sw.start();
            result = pjp.proceed();
            return result;
        } finally {
            sw.stop();
            log.info("Pointcut '{}',\n args: '{}',\n took '{}' milliseconds,\n result: '{}'\n",
                    pjp.toShortString(),
                    cvtToStr(pjp.getArgs()),
                    sw.getTotalTimeMillis(),
                    respToStr(result));
        }
    }

    private static String cvtToStr(Object[] args) {
        if (args == null)
            return "[ null ]";

        StringBuilder sb = new StringBuilder("[ ");
        for (Object o : args) {
            append(sb, o == null ? "null" : o.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

    private static String respToStr(Object o) {
        if (o == null)
            return "null";

        if (o instanceof ResponseEntity) {
            ResponseEntity respEntity = (ResponseEntity) o;
            StringBuilder sb = new StringBuilder("@ResponseEntity{ ");
            sb.append("statusCode: ").append(respEntity.getStatusCode()).append(", ");
            sb.append("body: ");

            Object body = respEntity.getBody();
            if (body == null) {
                sb.append("null");
            } else {
                if (body instanceof byte[]) {
                    sb.append(((byte[]) respEntity.getBody()).length).append(" bytes");
                } else {
                    String bodyAsStr;
                    try {
                        bodyAsStr = JsonUtils.writePretty(body);
                    } catch (JsonProcessingException e) {
                        bodyAsStr = body.toString();
                    }
                    sb.append(bodyAsStr);
                }
            }
            sb.append(" }");
            return sb.toString();
        } else {
            try {
                return JsonUtils.writePretty(o);
            } catch (JsonProcessingException e) {
                return o.toString();
            }
        }
    }

    private static void append(StringBuilder sb, String text) {
        // 2 is the length of ", "
        if (sb.length() > 2)
            sb.append(", ");

        sb.append("'").append(text).append("'");
    }
}
