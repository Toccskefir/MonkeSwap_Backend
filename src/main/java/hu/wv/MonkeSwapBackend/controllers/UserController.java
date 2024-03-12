package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.*;
import hu.wv.MonkeSwapBackend.services.ItemService;
import hu.wv.MonkeSwapBackend.services.TradeOfferService;
import hu.wv.MonkeSwapBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final ItemService itemService;
    private final TradeOfferService tradeOfferService;

    @Autowired
    public UserController(UserService userService, ItemService itemService, TradeOfferService tradeOfferService) {
        this.userService = userService;
        this.itemService = itemService;
        this.tradeOfferService = tradeOfferService;
    }

    //GET endpoints
    @GetMapping("/user")
    public UserDto getUser() {
        return this.userService.getUserFromContextHolder();
    }

    @GetMapping("/user/items")
    public List<ItemDto> getUserItems() {
        return this.itemService.getLoggedInUserItems();
    }

    @GetMapping("/user/items/{userId}")
    public List<ItemDto> getEnabledItemsByUserId(@PathVariable("userId")Long userId) {
        return this.itemService.getEnabledItemsByUserId(userId);
    }

    @GetMapping("/user/tradeoffers/offered")
    public List<TradeOfferDto> getOfferedTradeOffers() {
        return this.tradeOfferService.getOfferedTradeOffers();
    }

    @GetMapping("/user/tradeoffers/incoming")
    public List<TradeOfferDto> getIncomingTradeOffers() {
        return this.tradeOfferService.getIncomingTradeOffers();
    }

    @GetMapping("/admin/user/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId);
    }

    @GetMapping("/admin/users")
    public List<UserDto> getUsers() {
        return this.userService.getAllUsers();
    }

    //PUT endpoints
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateDto userDto) {
        this.userService.updateUser(userDto);
    }

    @PutMapping("/user/password")
    public void updateUserPassword(@RequestBody UserUpdatePasswordDto password) {
        this.userService.updateUserPassword(password);
    }

    @PutMapping("/admin/user/{userId}")
    public void updateUserRole(
            @PathVariable("userId")Long userId,
            @RequestBody String userRole) {
        this.userService.updateUserRole(userId, userRole);
    }

    //DELETE endpoints
    @DeleteMapping("/user")
    public void deleteUser() {
        this.userService.deleteUser();
    }

    @DeleteMapping("/admin/user/{userId}")
    public void deleteUserById(@PathVariable("userId") Long userId) {
        this.userService.deleteUserById(userId);
    }
}
