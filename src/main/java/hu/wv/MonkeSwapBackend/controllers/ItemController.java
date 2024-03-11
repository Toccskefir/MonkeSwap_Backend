package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public List<ItemDto> getEnabledItems() {
        return this.itemService.getEnabledItems();
    }

    @GetMapping("/items/{itemCategory}")
    public List<ItemDto> getEnabledItemsByCategory(@PathVariable("itemCategory")String itemCategory) {
        return this.itemService.getEnabledItemsByCategory(itemCategory);
    }

    @GetMapping("/item/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId")Long itemId) {
        return this.itemService.getItemById(itemId);
    }

    @GetMapping("/admin/items")
    public List<ItemDto> getReportedItems() {
        return this.itemService.getReportedItems();
    }

    @GetMapping("/admin/item/{itemId}")
    public ItemDto getReportedItemById(@PathVariable("itemId")Long itemId) {
        return this.itemService.getReportedItemById(itemId);
    }

    @PostMapping("/item")
    public void createItem(@RequestBody Item item) {
        this.itemService.createItem(item);
    }
}
