package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.repositories.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private List<ItemDto> convertItemToItemDto(List<Item> list) {
        List<ItemDto> listToReturn = new ArrayList<>();
        list.forEach(item -> {
            ItemDto itemDto = ItemDto.builder()
                    .id(item.getId())
                    .title(item.getTitle())
                    .itemPicture(item.getItemPicture())
                    .description(item.getDescription())
                    .views(item.getViews())
                    .state(item.getState())
                    .category(item.getCategory())
                    .priceTier(item.getPriceTier())
                    .build();
            listToReturn.add(itemDto);
        });
        return listToReturn;
    }

    public List<ItemDto> getEnabledItems() {
        List<Item> enabledItems = this.itemRepository.findAllByState(ItemState.ENABLED);
        return convertItemToItemDto(enabledItems);
    }

    public List<ItemDto> getEnabledItemsByCategory(String itemCategory) {
        ItemCategory category = ItemCategory.findByName(itemCategory);

        if (category == null) {
            throw new IllegalArgumentException("Given category not exists");
        }

        List<Item> enabledItemsByCategory =
                this.itemRepository.findAllByCategoryAndState(category, ItemState.ENABLED);
        return convertItemToItemDto(enabledItemsByCategory);
    }

    public List<ItemDto> getReportedItems() {
        List<Item> reportedItems = this.itemRepository.findAllByReportsGreaterThanEqual(5);
        return convertItemToItemDto(reportedItems);
    }

    @Transactional
    public void createItem(Item request) {
        if (request.getTitle().isBlank()) {
            throw new IsEmptyException("Title");
        }
        if (request.getItemPicture().isBlank()) {
            throw new IsEmptyException("Picture");
        }
        if (request.getDescription().isBlank()) {
            throw new IsEmptyException("Description");
        }

        Item item = Item.builder()
                .title(request.getTitle())
                .itemPicture(request.getItemPicture())
                .description(request.getDescription())
                .views(0)
                .reports(0)
                .state(ItemState.ENABLED)
                .category(request.getCategory())
                .priceTier(request.getPriceTier())
                .build();

        this.itemRepository.save(item);
    }
}
