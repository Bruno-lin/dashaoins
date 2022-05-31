package com.dsmh.common.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller请求日志打印
 *
 * @author Asher
 * @date 2022年2月21日 11:47
 */
@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Autowired
    private  ObjectMapper objectMapper;

    /**
     * 日志打印忽略
     */
    @Value("${web.api.log.except.uris:}")
    private List<String> logIgnoreUriList;


    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void requestPointCut() {
    }

    @Around("requestPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String methodClass = String.format("%s-%s", proceedingJoinPoint.getSignature().getDeclaringTypeName()
                , proceedingJoinPoint.getSignature().getName());

        Object result = proceedingJoinPoint.proceed();

        if (logIgnoreUriList.contains(request.getRequestURI())) {
            return result;
        }

        RequestInfo requestInfo = RequestInfo.builder()
                .url(request.getRequestURL().toString())
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .method(methodClass)
                .result(result)
                .headerInfo(getHeaderInfo(request))
                .methodParams(getMethodParams(proceedingJoinPoint))
                .timeCost(System.currentTimeMillis() - start)
                .build();
        printLogInfo(requestInfo, "INFO");
        return result;
    }

    @AfterThrowing(pointcut = "requestPointCut()", throwing = "ex")
    public void doAfterThrow(JoinPoint joinPoint, Exception ex) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String methodClass = String.format("%s-%s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        RequestInfo errorRequestInfo = RequestInfo.builder()
                .url(request.getRequestURL().toString())
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .method(methodClass)
                .headerInfo(getHeaderInfo(request))
                .methodParams(getMethodParams(joinPoint))
                .exMsg(ex.getMessage())
                .build();
        printLogInfo(errorRequestInfo, "ERROR");
    }

    private Map<String, Object> getHeaderInfo(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headerMap.put(name, value);
        }
        return headerMap;
    }

    private Map<String, Object>  getMethodParams(JoinPoint joinPoint) {
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] values = joinPoint.getArgs();
        return buildMethodParams(paramNames, values);
    }

    private Map<String, Object> buildMethodParams(String[] paramNames, Object[] values) {
        Map<String, Object> requestParams = new HashMap<>(paramNames.length);
        for (int i = 0; i < paramNames.length; i++) {
            Object value = values[i];
            if (value instanceof MultipartFile) {
                //如果是文件类型，保存文件名
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();
            } else if (value instanceof MultipartFile[]) {
                //处理参数类型为数组文件类型
                value = handleArrayMultipartFileType((MultipartFile[]) value);
            } else if (
                    value instanceof ServletRequest
                            || value instanceof ServletResponse
                            || value instanceof InputStream
                            || value instanceof InputStreamSource
            ) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }

    private String handleArrayMultipartFileType(MultipartFile[] files) {
        StringBuilder filenames = new StringBuilder();
        String separator = ",";
        if (files.length > 0) {
            for (MultipartFile file : files) {
                filenames.append(file.getOriginalFilename()).append(separator);
            }
        }
        return filenames.deleteCharAt(filenames.lastIndexOf(separator)).toString();
    }

    private void printLogInfo(RequestInfo logInfo, String level) {
        try {
            log.info("###[{}]Request Info: {}", level, objectMapper.writeValueAsString(logInfo));
        } catch (Exception exception) {
            log.error("###Component-Logger-Controller print log error", exception);
        }
    }

    @Data
    @Builder
    static class RequestInfo implements Serializable {
        /**
         * 请求url
         */
        private String url;
        /**
         * 发起请求ip
         */
        private String ip;
        /**
         * 请求方式
         */
        private String requestType;
        /**
         * 方法名
         */
        private String method;
        /**
         * 请求结果
         */
        private Object result;
        /**
         * 请求头信息
         */
        private Object headerInfo;
        /**
         * controller方法入参
         */
        private Object methodParams;
        /**
         * 错误信息
         */
        private String exMsg;
        /**
         * 接口调用时间
         */
        private Long timeCost;
    }
}
