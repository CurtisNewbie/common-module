package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.data.BiContainer;
import com.curtisnewbie.common.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


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

    private static final List<BiContainer<Class, PrintAdapter>> printAdapters = new ArrayList<>();

    static {
        printAdapters.add(new BiContainer<>(byte[].class, (o) -> ((byte[]) o).length + " bytes"));
        printAdapters.add(new BiContainer<>(ResponseEntity.class, o -> {
            ResponseEntity respEntity = ((ResponseEntity) o);
            StringBuilder sb = new StringBuilder("@ResponseEntity{ ");
            sb.append("statusCode: ").append(respEntity.getStatusCode()).append(", ");
            sb.append("body: ");

            Object body = respEntity.getBody();
            if (body == null) {
                sb.append("null");
            } else {
                sb.append(print(respEntity.getBody()));
            }
            sb.append(" }");
            return sb.toString();
        }));
        printAdapters.add(new BiContainer<>(InputStream.class, Object::toString));
        printAdapters.add(new BiContainer<>(OutputStream.class, Object::toString));
        printAdapters.add(new BiContainer<>(ServletRequest.class, Object::toString));
        printAdapters.add(new BiContainer<>(ServletResponse.class, Object::toString));
        printAdapters.add(new BiContainer<>(DeferredResult.class, Object::toString));
        printAdapters.add(new BiContainer<>(Runnable.class, Object::toString));
        printAdapters.add(new BiContainer<>(Callable.class, Object::toString));
        printAdapters.add(new BiContainer<>(Future.class, Object::toString));
        printAdapters.add(new BiContainer<>(StreamingResponseBody.class, Object::toString));
    }

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
            log.info("Pointcut '{}',\n args: '{}',\n took '{}' milliseconds,\n result: '{}'\n", pjp.toShortString(), cvtToStr(pjp.getArgs()), sw.getTotalTimeMillis(), print(result));
        }
    }

    private static String cvtToStr(Object[] args) {
        if (args == null) return "[ null ]";

        StringBuilder sb = new StringBuilder("[ ");
        for (Object o : args) {
            append(sb, o == null ? "null" : o.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

    private static void append(StringBuilder sb, String text) {
        // 2 is the length of ", "
        if (sb.length() > 2) sb.append(", ");
        sb.append("'").append(text).append("'");
    }

    private static <T> String print(T t) {
        if (t == null) return "null";
        for (BiContainer<Class, PrintAdapter> printAdapter : printAdapters) {
            if (printAdapter.getLeft().isAssignableFrom(t.getClass())) {
                return printAdapter.getRight().print(t);
            }
        }
        try {
            return JsonUtils.writePretty(t);
        } catch (JsonProcessingException e) {
            return t.toString();
        }
    }

    @FunctionalInterface
    public interface PrintAdapter {

        String print(Object t);
    }
}
