package com.hcmute.yourtours.security.jwt;

import com.hcmute.yourtours.security.properties.SecurityConfigurationProperties;
import com.hcmute.yourtours.security.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService customUserDetailsService;

    @Autowired
    private SecurityConfigurationProperties securityConfigurationProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // Lấy jwt từ request
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                // Lấy id user từ chuỗi jwt
                UUID userId = tokenProvider.getUserIdFromJwt(jwt);

                // Lấy thông tin người dùng từ id
                UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
                if (userDetails != null) {

                    // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        SecurityConfigurationProperties.Jwt jwtConfig = this.securityConfigurationProperties.getJwt();

        String bearerToken = request.getHeader(jwtConfig.getHeaderString());

        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getTokenPrefix() + " ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
