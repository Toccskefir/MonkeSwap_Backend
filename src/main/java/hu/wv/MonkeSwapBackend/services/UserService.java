package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.UserDto;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import hu.wv.MonkeSwapBackend.utils.UserUtil;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto convertUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getRealUsername())
                .tradesCompleted(user.getTradesCompleted())
                .role(user.getRole())
                .dateOfRegistration(user.getDateOfRegistration())
                .fullName(user.getFullName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(user.getProfilePicture())
                .build();
    }

    public UserDto getUserFromContextHolder() {
        return convertUserToUserDto(UserUtil.getUserFromContextHolder());
    }

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return this.convertUserToUserDto(user.get());
        } else {
            throw new ObjectNotFoundException("userId", id);
        }
    }

    public List<UserDto> getAllUsers() {
        List<User> usersList = userRepository.findAll();
        List<UserDto> responseList = new ArrayList<>();
        usersList.forEach(item -> {
            UserDto userDto = this.convertUserToUserDto(item);
            responseList.add(userDto);
        });
        return responseList;
    }
}
