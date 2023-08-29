package com.post.postplatform.context;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class PostRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> headers = request.getHeaderNames();
        Map<String, String> requestHeaders = new HashMap<>();
        String header;
        while (headers.hasMoreElements()){
            header = headers.nextElement();
            requestHeaders.put(header, request.getHeader(header));
        }
        RequestContainer.setRequestContext(new RequestContext(requestHeaders));
        try {
            filterChain.doFilter(request, response);
        }
        finally {
            RequestContainer.unsetRequestContext();
        }
    }
}
