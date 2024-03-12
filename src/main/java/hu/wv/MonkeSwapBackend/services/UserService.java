package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.UserDto;
import hu.wv.MonkeSwapBackend.dtos.UserUpdateDto;
import hu.wv.MonkeSwapBackend.dtos.UserUpdatePasswordDto;
import hu.wv.MonkeSwapBackend.enums.UserRole;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.exceptions.IsRegisteredException;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import hu.wv.MonkeSwapBackend.utils.CommonUtil;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
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

    //READ methods
    public UserDto getUserFromContextHolder() {
        return convertUserToUserDto(CommonUtil.getUserFromContextHolder());
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

    //UPDATE methods
    @Transactional
    public void updateUser(UserUpdateDto userDto) {
        User user = CommonUtil.getUserFromContextHolder();

        if (this.userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IsRegisteredException("Username is taken");
        }
        if (userDto.getUsername().isBlank()) {
            throw new IsEmptyException("Username");
        }

        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setProfilePicture(userDto.getProfilePicture());
    }

    @Transactional
    public void updateUserPassword(UserUpdatePasswordDto password) {
        User user = CommonUtil.getUserFromContextHolder();

        if(password.getPassword().isBlank()) {
            throw new IsEmptyException("Password");
        }

        user.setPassword(encoder.encode(password.getPassword()));
    }

    @Transactional
    public void updateUserRole(Long id, String userRole) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("userId", id));
        UserRole role = UserRole.findByName(userRole);

        if(role == null) {
            throw new IllegalArgumentException("Given role not exists");
        }
        if(id == 1) {
            throw new IllegalArgumentException("Can not update this user's role");
        }

        user.setRole(role);
    }

    //DELETE methods
    public void deleteUser() {
        Long userId = CommonUtil.getUserFromContextHolder().getId();
        if (userId == 1) {
            throw new IllegalArgumentException("Can not delete this user");
        }
        this.userRepository.deleteById(userId);
    }

    public void deleteUserById(Long id) {
        if (id == 1) {
            throw new IllegalArgumentException("Can not delete this user");
        }

        Optional<User> userToDelete = this.userRepository.findById(id);
        if (userToDelete.isPresent()) {
            this.userRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("userId", id);
        }
    }
}
