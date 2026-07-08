package com.infraops.infraops_backend.security;

import com.infraops.infraops_backend.model.Role;
import com.infraops.infraops_backend.model.User;
import com.infraops.infraops_backend.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
        
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String provider = token.getAuthorizedClientRegistrationId(); // "google" or "github"

        if (email == null) {
            // For GitHub, email might be private or not present in the default attributes if not requested properly
            // In a real app we might need to fetch it from github api or handle it.
            // Assuming email is present for now.
            email = oAuth2User.getAttribute("login") + "@github.com"; // Fallback for github
        }

        // Find or create user
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            User newUser = User.builder()
                    .email(email)
                    .firstName(name != null ? name.split(" ")[0] : email.split("@")[0])
                    .lastName(name != null && name.contains(" ") ? name.substring(name.indexOf(" ") + 1) : "")
                    .role(Role.USER) // Default role
                    .authProvider(provider)
                    .build();
            userRepository.save(newUser);
        } else {
            User existingUser = userOptional.get();
            if (existingUser.getAuthProvider() == null) {
                existingUser.setAuthProvider(provider);
                userRepository.save(existingUser);
            }
        }

        // Generate JWT
        String jwt = jwtUtils.generateJwtTokenFromUsername(email);

        // Redirect to frontend with JWT
        String targetUrl = frontendUrl + "/oauth2/redirect?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
