package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.*;
import hu.wv.MonkeSwapBackend.services.ItemService;
import hu.wv.MonkeSwapBackend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Returns the data of the logged-in user")
    @GetMapping("/user")
    public UserDto getUser() {
        return this.userService.getUserFromContextHolder();
    }

    @Operation(summary = "Returns a list of items of the logged-in user")
    @GetMapping("/user/items")
    public List<ItemDto> getUserItems() {
        return this.itemService.getLoggedInUserItems();
    }

    @Operation(summary = "Returns a list of enabled items of a user by userId")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User and it's items found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemDto.class)))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/user/items/{userId}")
    public List<ItemDto> getEnabledItemsByUserId(@PathVariable("userId")Long userId) {
        return this.itemService.getEnabledItemsByUserId(userId);
    }

    @Operation(summary = "Returns the data of a user by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/admin/user/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId);
    }

    @Operation(summary = "Returns a list of users")
    @GetMapping("/admin/users")
    public List<UserDto> getUsers() {
        return this.userService.getAllUsers();
    }

    //PUT endpoints
    @Operation(summary = "Updates the logged-in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateDto userDto) {
        this.userService.updateUser(userDto);
    }

    @Operation(summary = "Updates the username of the logged-in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User's username successfully updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
    @PutMapping("/user/username")
    public void updateUserUsername(@RequestBody UserUpdateUsernameDto usernameDto) {
        this.userService.updateUserUsername(usernameDto);
    }

    @Operation(summary = "Updates the profile picture of the logged-in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User's profile picture successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserUpdateProfilePictureDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
    @PutMapping("/user/profilepicture")
    public UserUpdateProfilePictureDto updateUserProfilePicture(@RequestPart byte[] profilePicture) {
        return this.userService.updateUserProfilePicture(profilePicture);
    }

    @Operation(summary = "Updates the password of the logged-in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User's password successfully updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
    @PutMapping("/user/password")
    public void updateUserPassword(@RequestBody UserUpdatePasswordDto password) {
        this.userService.updateUserPassword(password);
    }

    @Operation(summary = "Updates the role of a user by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User's role successfully updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/admin/user/{userId}")
    public void updateUserRole(
            @PathVariable("userId")Long userId,
            @RequestBody String userRole) {
        this.userService.updateUserRole(userId, userRole);
    }

    //DELETE endpoints
    @Operation(summary = "Deletes the logged-in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Unable to delete this user", content = @Content)
    })
    @DeleteMapping("/user")
    public void deleteUser() {
        this.userService.deleteUser();
    }

    @Operation(summary = "Deletes a user by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Unable to delete this user", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/admin/user/{userId}")
    public void deleteUserById(@PathVariable("userId") Long userId) {
        this.userService.deleteUserById(userId);
    }
}
