package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.ItemRepository;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
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

    //returns the logged-in user
    private User getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("Anonymous request");
        } else {
            String currentPrincipalName = authentication.getName();
            return userRepository.findByEmail(currentPrincipalName).get();
        }
    }

    //returns all enabled items except the items of the logged-in user
    public List<ItemDto> getEnabledItems() {
        List<Item> enabledItems = this.itemRepository.findAllByStateAndUserIdNot(ItemState.ENABLED, getCurrentUserId());
        return convertItemToItemDto(enabledItems);
    }

    //returns all enabled items by category except the items of the logged-in user
    public List<ItemDto> getEnabledItemsByCategory(String itemCategory) {
        ItemCategory category = ItemCategory.findByName(itemCategory);

        if (category == null) {
            throw new IllegalArgumentException("Given category not exists");
        }

        List<Item> enabledItemsByCategory =
                this.itemRepository.findAllByCategoryAndStateAndUserIdNot(category, ItemState.ENABLED, getCurrentUserId());
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
