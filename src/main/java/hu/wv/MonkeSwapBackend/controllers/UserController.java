package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.dtos.UserDto;
import hu.wv.MonkeSwapBackend.dtos.UserUpdateDto;
import hu.wv.MonkeSwapBackend.dtos.UserUpdatePasswordDto;
import hu.wv.MonkeSwapBackend.services.ItemService;
import hu.wv.MonkeSwapBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    //GET endpoints
    //returns the data of the logged-in user
    @GetMapping("/user")
    public UserDto getUser() {
        return this.userService.getUserFromContextHolder();
    }

    //returns a list of items of the logged-in user
    @GetMapping("/user/items")
    public List<ItemDto> getUserItems() {
        return this.itemService.getLoggedInUserItems();
    }

    //returns a list of enabled items of a user by userId
    @GetMapping("/user/items/{userId}")
    public List<ItemDto> getEnabledItemsByUserId(@PathVariable("userId")Long userId) {
        return this.itemService.getEnabledItemsByUserId(userId);
    }

    //returns tha data of a user by id
    @GetMapping("/admin/user/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId);
    }

    //returns a list of users
    @GetMapping("/admin/users")
    public List<UserDto> getUsers() {
        return this.userService.getAllUsers();
    }

    //PUT endpoints
    //updates the logged-in user
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateDto userDto) {
        this.userService.updateUser(userDto);
    }

    //updates the password of the logged-in user
    @PutMapping("/user/password")
    public void updateUserPassword(@RequestBody UserUpdatePasswordDto password) {
        this.userService.updateUserPassword(password);
    }

    //updates the role of a user by id
    @PutMapping("/admin/user/{userId}")
    public void updateUserRole(
            @PathVariable("userId")Long userId,
            @RequestBody String userRole) {
        this.userService.updateUserRole(userId, userRole);
    }

    //DELETE endpoints
    //deletes the logged-in user
    @DeleteMapping("/user")
    public void deleteUser() {
        this.userService.deleteUser();
    }

    //deletes a user by id
    @DeleteMapping("/admin/user/{userId}")
    public void deleteUserById(@PathVariable("userId") Long userId) {
        this.userService.deleteUserById(userId);
    }
}
