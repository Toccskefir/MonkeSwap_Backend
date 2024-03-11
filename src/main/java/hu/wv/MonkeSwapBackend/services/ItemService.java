package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.ItemRepository;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import hu.wv.MonkeSwapBackend.utils.CommonUtil;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    private ItemDto convertItemToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .itemPicture(item.getItemPicture())
                .description(item.getDescription())
                .views(item.getViews())
                .state(item.getState())
                .category(item.getCategory())
                .priceTier(item.getPriceTier())
                .build();
    }

    //returns all enabled items except the items of the logged-in user
    public List<ItemDto> getEnabledItems() {
        List<Item> enabledItems = this.itemRepository
                .findAllByStateAndUserIdNot(ItemState.ENABLED, CommonUtil.getUserFromContextHolder());
        return CommonUtil.convertItemListToItemDtoList(enabledItems);
    }

    //returns all enabled items by category except the items of the logged-in user
    public List<ItemDto> getEnabledItemsByCategory(String itemCategory) {
        ItemCategory category = ItemCategory.findByName(itemCategory);

        if (category == null) {
            throw new IllegalArgumentException("Given category not exists");
        }

        List<Item> enabledItemsByCategory = this.itemRepository
                .findAllByCategoryAndStateAndUserIdNot(category, ItemState.ENABLED, CommonUtil.getUserFromContextHolder());
        return CommonUtil.convertItemListToItemDtoList(enabledItemsByCategory);
    }

    public List<ItemDto> getEnabledItemsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Item> items = itemRepository.findAllByUserIdAndState(user.get(), ItemState.ENABLED);
            return CommonUtil.convertItemListToItemDtoList(items);
        } else {
            throw new ObjectNotFoundException("userId", userId);
        }
    }

    public List<ItemDto> getLoggedInUserItems() {
        List<Item> items = this.itemRepository
                .findAllByUserId(CommonUtil.getUserFromContextHolder());
        return CommonUtil.convertItemListToItemDtoList(items);
    }

    public ItemDto getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return this.convertItemToItemDto(item.get());
        } else {
            throw new ObjectNotFoundException("itemId", id);
        }
    }

    public ItemDto getReportedItemById(Long id) {
        Optional<Item> item = itemRepository.findByIdAndReportsGreaterThan(id, 5);
        if (item.isPresent()) {
            return this.convertItemToItemDto(item.get());
        } else {
            throw new ObjectNotFoundException("itemId", id);
        }
    }

    public List<ItemDto> getReportedItems() {
        List<Item> reportedItems = this.itemRepository.findAllByReportsGreaterThanEqual(5);
        return CommonUtil.convertItemListToItemDtoList(reportedItems);
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

    public void deleteItemById(Long id) {
        User user = CommonUtil.getUserFromContextHolder();
        Optional<Item> item = itemRepository.findByIdAndUserId(id, user);

        if (item.isPresent()) {
            this.itemRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("itemId", id);
        }
    }

    public void deleteAnyItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);

        if (item.isPresent()) {
            this.itemRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("itemId", id);
        }
    }
}
