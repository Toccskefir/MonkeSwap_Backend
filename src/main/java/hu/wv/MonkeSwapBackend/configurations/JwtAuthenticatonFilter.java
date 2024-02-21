package hu.wv.MonkeSwapBackend.configurations;

import hu.wv.MonkeSwapBackend.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticatonFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authenticationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String email;
        if (authenticationHeader == null || !authenticationHeader.startsWith("Beaer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authenticationHeader.substring(7);
        email = jwtService.extractUsername(jwtToken);
    }
}
