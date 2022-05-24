package kr.sproutfx.oauth.backoffice.common.aspect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Aspect
public class RestControllerAspect {

    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(RestControllerAspect.class);

    public RestControllerAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PatchMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    private void onRequest() { }

    @Around("onRequest()")
    private Object doRequestLogging(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String method = httpServletRequest.getMethod();
        String uri = httpServletRequest.getRequestURI();
        Map<String, List<String>> headers = Collections.list(httpServletRequest.getHeaderNames())
            .stream()
            .collect(Collectors.toMap(Function.identity(), header -> Collections.list(httpServletRequest.getHeaders(header))));
        Map<String, String[]> params = httpServletRequest.getParameterMap();

        InputStream bodyInputStream = httpServletRequest.getInputStream();

        Map<String, Object> body = (bodyInputStream.available() != 0) ? objectMapper.readValue(bodyInputStream, Map.class) : null;

        Object responseBody = null;

        long startTimeMillis = System.currentTimeMillis();
        try {
            responseBody = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            return responseBody;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            long endTimeMillis = System.currentTimeMillis();
            long duration = endTimeMillis - startTimeMillis;
            logger.debug(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new StructuredRestControllerLog(method, uri, headers, params, body, duration, responseBody)));
        }
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    static class StructuredRestControllerLog {
        @JsonProperty(value = "request-method")
        private final String requestMethod;
        @JsonProperty(value = "request-uri")
        private final String requestUri;
        @JsonProperty(value = "request-headers")
        private final Map<String, List<String>> requestHeaders;
        @JsonProperty(value = "request-params")
        private final Map<String, String[]> requestParams;
        @JsonProperty(value = "request-body")
        private Map<String, Object> requestBody;
        @JsonProperty(value = "response-body")
        private final Object responseBody;
        @JsonProperty(value = "duration")
        private final String duration;

        public StructuredRestControllerLog(String requestMethod, String requestUri, Map<String, List<String>> requestHeaders, Map<String, String[]> requestParams, Map<String, Object> requestBody, Long duration, Object responseBody) {
            this.requestMethod = requestMethod;
            this.requestUri = requestUri;
            this.requestHeaders = requestHeaders;
            this.requestParams = requestParams;
            this.requestBody = requestBody;
            this.duration = String.format("%d ms", duration);
            this.responseBody = responseBody;
        }
    }
}
