package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.dtos.ItemCreateDto;
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

    //GET endpoints
    //returns a list of enabled items except the items of the logged-in user
    @GetMapping("/items")
    public List<ItemDto> getEnabledItems() {
        return this.itemService.getEnabledItems();
    }

    //returns a list of enabled items by category except the items of the logged-in user
    @GetMapping("/items/{itemCategory}")
    public List<ItemDto> getEnabledItemsByCategory(@PathVariable("itemCategory")String itemCategory) {
        return this.itemService.getEnabledItemsByCategory(itemCategory);
    }

    //returns an item by id
    @GetMapping("/item/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId")Long itemId) {
        return this.itemService.getItemById(itemId);
    }

    //returns a list of reported items
    @GetMapping("/admin/items")
    public List<ItemDto> getReportedItems() {
        return this.itemService.getReportedItems();
    }

    //returns a reported item by id
    @GetMapping("/admin/item/{itemId}")
    public ItemDto getReportedItemById(@PathVariable("itemId")Long itemId) {
        return this.itemService.getReportedItemById(itemId);
    }

    //POST endpoints
    //creates an item
    @PostMapping("/item")
    public void createItem(
            @RequestPart String title,
            @RequestPart byte[] itemPicture,
            @RequestPart String description,
            @RequestPart String category,
            @RequestPart String priceTier) {
        ItemCreateDto itemDto = ItemCreateDto.builder()
                .title(title)
                .itemPicture(itemPicture)
                .description(description)
                .category(category)
                .priceTier(Integer.parseInt(priceTier))
                .build();
        this.itemService.createItem(itemDto);
    }

    //PUT endpoints
    //updates an item by id
    @PutMapping("/item/{itemId}")
    public ItemDto updateItemById(
            @PathVariable("itemId")Long itemId,
            @RequestPart String title,
            @RequestPart byte[] itemPicture,
            @RequestPart String description,
            @RequestPart String category,
            @RequestPart String priceTier) {
        ItemCreateDto itemDto = ItemCreateDto.builder()
                .title(title)
                .itemPicture(itemPicture)
                .description(description)
                .category(category)
                .priceTier(Integer.parseInt(priceTier))
                .build();
        return this.itemService.updateItemById(itemId, itemDto);
    }

    //increases the views of an item by id
    @PutMapping("/item/views/{itemId}")
    public void updateItemViews(@PathVariable("itemId")Long itemId) {
        this.itemService.updateItemViews(itemId);
    }

    //adds the logged-in user to the reports list of an item by id
    @PutMapping("/item/reports/{itemId}")
    public void updateItemReports(@PathVariable("itemId")Long itemId) {
        this.itemService.updateItemReports(itemId);
    }

    //updates the state of an item by id
    @PutMapping("/admin/item/{itemId}")
    public void updateItemState(
            @PathVariable("itemId")Long itemId,
            @RequestBody String itemState) {
        this.itemService.updateItemState(itemId, itemState);
    }

    //DELETE endpoints
    //deletes an item of the logged-in user by id
    @DeleteMapping("/item/{itemId}")
    public void deleteItemById(@PathVariable("itemId")Long itemId) {
        this.itemService.deleteItemById(itemId);
    }

    //deletes an item by id
    @DeleteMapping("/admin/item/{itemId}")
    public void deleteAnyItemById(@PathVariable("itemId")Long itemId) {
        this.itemService.deleteAnyItemById(itemId);
    }
}
