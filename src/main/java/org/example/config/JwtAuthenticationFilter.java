//package org.example.config;
//
//import org.example.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//
//    @Autowired
//    private JwtTokenUtil tokenProvider;
//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
//        try {
//            String jwt = getJwtFromRequest(request);
//
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                String username = tokenProvider.getUsernameFromToken(jwt);
//                UserDetails userDetails = userService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception ex) {
//            logger.error("Could not set user authentication in security context", ex);
//        }
//
//        try {
//            filterChain.doFilter(request, response);
//        } catch (IOException ex) {
//            logger.error("IOException in JwtAuthenticationFilter", ex);
//        } catch (ServletException ex) {
//            logger.error("ServletException in JwtAuthenticationFilter", ex);
//        }
//    }
//
//
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
