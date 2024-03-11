package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.dtos.TradeOfferDto;
import hu.wv.MonkeSwapBackend.dtos.UserDto;
import hu.wv.MonkeSwapBackend.services.ItemService;
import hu.wv.MonkeSwapBackend.services.TradeOfferService;
import hu.wv.MonkeSwapBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
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
}
