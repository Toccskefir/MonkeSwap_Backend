package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.ItemCreateDto;
import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.services.ItemService;
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
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //GET endpoints
    @Operation(summary = "Returns a list of enabled items except the items of the logged-in user")
    @GetMapping("/items")
    public List<ItemDto> getEnabledItems() {
        return this.itemService.getEnabledItems();
    }

    @Operation(summary = "Returns a list of enabled items by category except the items of the logged-in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Items found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
    @GetMapping("/items/{itemCategory}")
    public List<ItemDto> getEnabledItemsByCategory(@PathVariable("itemCategory")String itemCategory) {
        return this.itemService.getEnabledItemsByCategory(itemCategory);
    }

    @Operation(summary = "Returns an item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("/item/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId")Long itemId) {
        return this.itemService.getItemById(itemId);
    }

    @Operation(summary = "Returns a list of reported items")
    @GetMapping("/admin/items")
    public List<ItemDto> getReportedItems() {
        return this.itemService.getReportedItems();
    }

    @Operation(summary = "Returns a reported item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("/admin/item/{itemId}")
    public ItemDto getReportedItemById(@PathVariable("itemId")Long itemId) {
        return this.itemService.getReportedItemById(itemId);
    }

    //POST endpoints
    @Operation(summary = "Creates an item")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item successfully created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
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
    @Operation(summary = "Updates an item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
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

    @Operation(summary = "Increases the views of an item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item's views successfully increased", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @PutMapping("/item/views/{itemId}")
    public void updateItemViews(@PathVariable("itemId")Long itemId) {
        this.itemService.updateItemViews(itemId);
    }

    @Operation(summary = "Adds the logged-in user to the reports field of an item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item's reports successfully updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Item already reported", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @PutMapping("/item/reports/{itemId}")
    public void updateItemReports(@PathVariable("itemId")Long itemId) {
        this.itemService.updateItemReports(itemId);
    }

    @Operation(summary = "Updates the state of an item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item's state successfully updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @PutMapping("/admin/item/{itemId}")
    public void updateItemState(
            @PathVariable("itemId")Long itemId,
            @RequestBody String itemState) {
        this.itemService.updateItemState(itemId, itemState);
    }

    //DELETE endpoints
    @Operation(summary = "Deletes an item of the logged-in user by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @DeleteMapping("/item/{itemId}")
    public void deleteItemById(@PathVariable("itemId")Long itemId) {
        this.itemService.deleteItemById(itemId);
    }

    @Operation(summary = "Deletes an item by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Item successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @DeleteMapping("/admin/item/{itemId}")
    public void deleteAnyItemById(@PathVariable("itemId")Long itemId) {
        this.itemService.deleteAnyItemById(itemId);
    }
}
