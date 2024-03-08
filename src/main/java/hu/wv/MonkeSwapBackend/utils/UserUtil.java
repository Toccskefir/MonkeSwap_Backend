package hu.wv.MonkeSwapBackend.utils;

import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtil {
    private UserRepository userRepository;

    public User getUserFromContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("Anonymous request");
        } else {
            String currentPrincipalName = authentication.getName();
            return userRepository.findByEmail(currentPrincipalName).get();
        }
    }
}
