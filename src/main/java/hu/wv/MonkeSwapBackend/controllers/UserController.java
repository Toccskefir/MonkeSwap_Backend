package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.UserDto;
import hu.wv.MonkeSwapBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user")
    public UserDto getUser() {
        return this.userService.getUserFromContextHolder();
    }

    @GetMapping("/admin/user/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId);
    }

    @GetMapping("/admin/users")
    public List<UserDto> getUsers() {
        return this.userService.getAllUsers();
    }
}
