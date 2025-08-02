package com.aos.AOSBE.SecurityConfig;

import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.CartItemsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtil jwtTokenProvider;
    @Autowired
    CartItemsService cartItemsService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Sinh JWT token
        String token = jwtTokenProvider.generateToken(email);
        response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + token+
                "&cartSize=" + cartItemsService.cartItemsFindAccounts(email).stream().reduce(0, (a, b) -> a + b.getQty(), Integer::sum));
    }
}
