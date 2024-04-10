package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.*;
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
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    //READ methods
    public UserDto getUserFromContextHolder() {
        return CommonUtil.convertUserToUserDto(CommonUtil.getUserFromContextHolder());
    }

    public UserDto getUserById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("userId", id));
        return CommonUtil.convertUserToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> usersList = this.userRepository.findAll();
        List<UserDto> responseList = new ArrayList<>();
        usersList.forEach(item -> {
            UserDto userDto = CommonUtil.convertUserToUserDto(item);
            responseList.add(userDto);
        });
        return responseList;
    }

    //UPDATE methods
    @Transactional
    public void updateUser(UserUpdateDto userDto) {
        User loggedInUser = CommonUtil.getUserFromContextHolder();

        if (!Objects.equals(userDto.getUsername(), loggedInUser.getRealUsername())) {
            if (this.userRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new IsRegisteredException("Username is taken");
            }
        }
        if (userDto.getUsername().isBlank()) {
            throw new IsEmptyException("Username");
        }

        loggedInUser.setUsername(userDto.getUsername());
        loggedInUser.setFullName(userDto.getFullName());
        loggedInUser.setDateOfBirth(userDto.getDateOfBirth());
        loggedInUser.setPhoneNumber(userDto.getPhoneNumber());
    }

    @Transactional
    public void updateUserUsername(UserUpdateUsernameDto usernameDto) {
        User loggedInUser = CommonUtil.getUserFromContextHolder();

        if (!Objects.equals(usernameDto.getUsername(), loggedInUser.getRealUsername())) {
            if (this.userRepository.findByUsername(usernameDto.getUsername()).isPresent()) {
                throw new IsRegisteredException("Username is taken");
            }
        }
        if(usernameDto.getUsername().isBlank()) {
            throw new IsEmptyException("Username");
        }

        loggedInUser.setUsername(usernameDto.getUsername());
    }

    @Transactional
    public void updateUserProfilePicture(byte[] profilePicture) {
        User loggedInUser = CommonUtil.getUserFromContextHolder();

        if (profilePicture == null) {
            throw new IsEmptyException("Profile picture");
        }

        loggedInUser.setProfilePicture(profilePicture);
    }

    @Transactional
    public void updateUserPassword(UserUpdatePasswordDto password) {
        User loggedInUser = CommonUtil.getUserFromContextHolder();

        if(password.getPassword().isBlank()) {
            throw new IsEmptyException("Password");
        }

        loggedInUser.setPassword(encoder.encode(password.getPassword()));
    }

    @Transactional
    public void updateUserRole(Long id, String userRole) {
        if (id == 1) {
            throw new IllegalArgumentException("Can not update this user's role");
        }

        User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("userId", id));
        if (user.getId().equals(CommonUtil.getUserFromContextHolder().getId())) {
            throw new IllegalArgumentException("Can not update own user role");
        }

        UserRole role = UserRole.findByName(userRole);
        if (role == null) {
            throw new IllegalArgumentException("Given role not exists");
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

        User userToDelete = this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("userId", id));
        if (userToDelete.getId().equals(CommonUtil.getUserFromContextHolder().getId())) {
            throw new IllegalArgumentException("Can not delete own user");
        }

        this.userRepository.deleteById(id);
    }
}
