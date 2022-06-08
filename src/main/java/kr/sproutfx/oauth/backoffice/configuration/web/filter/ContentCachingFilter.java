package kr.sproutfx.oauth.backoffice.configuration.web.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {
    @Value("${sproutfx.web.filter.content-caching-filter.ignore.regex:}")
    private String contentCachingFilterIgnoreRegex;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!contentCachingFilterIgnoreRegex.equals(StringUtils.EMPTY) &&
                Pattern.matches(contentCachingFilterIgnoreRegex, httpServletRequest.getRequestURI())) {

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
            filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
        }
    }
}