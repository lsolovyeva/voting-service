/*package org.example.exception;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Order(1)
@Component
public class AccessDeniedExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain fc) throws ServletException, IOException {
        try {
            fc.doFilter(request, response);
        } catch (AccessDeniedException e) {
            // log error if needed here then redirect
            log.info("Apply Exception Filter");

            RequestDispatcher requestDispatcher =
                    getServletContext().getRequestDispatcher("/");
            requestDispatcher.forward(request, response);

        }
    }
}
*/