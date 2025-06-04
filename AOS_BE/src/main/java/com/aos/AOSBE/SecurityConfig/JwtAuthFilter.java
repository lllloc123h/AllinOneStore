//package com.aos.AOSBE.SecurityConfig;
//
//import java.io.IOException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//	  private final JwtUtil jwtUtil = new JwtUtil(); // This is fine as it's not Spring-managed
//	  private final UserDetailService userDetailService;
//
//	    public JwtAuthFilter(UserDetailService userDetailService) {
//	        this.userDetailService = userDetailService;
//	    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//                                    throws ServletException, IOException {
//    	 String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            String username = jwtUtil.extractUsername(token);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailService.loadUserByUsername(username);
//
//           	 
//                if (jwtUtil.isTokenValid(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//	
//}
