package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.dtos.ItemUpdateDto;
import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.ItemRepository;
import hu.wv.MonkeSwapBackend.repositories.TradeOfferRepository;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import hu.wv.MonkeSwapBackend.utils.CommonUtil;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final TradeOfferRepository tradeOfferRepository;

    @Autowired
    public ItemService(
            ItemRepository itemRepository,
            UserRepository userRepository,
            TradeOfferRepository tradeOfferRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.tradeOfferRepository = tradeOfferRepository;
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

    //READ methods
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
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        if (item.getReports().size() < 5) {
            throw new ObjectNotFoundException("itemId", id);
        }
        return this.convertItemToItemDto(item);
    }

    public List<ItemDto> getReportedItems() {
        List<Item> items = this.itemRepository.findAll();
        List<Item> reportedItems = new ArrayList<>();
        items.forEach(item -> {
            if (item.getReports().size() >= 5) {
                reportedItems.add(item);
            }
        });
        return CommonUtil.convertItemListToItemDtoList(reportedItems);
    }

    //CREATE methods
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
                .state(ItemState.ENABLED)
                .category(request.getCategory())
                .priceTier(request.getPriceTier())
                .build();

        this.itemRepository.save(item);
    }

    //UPDATE methods
    @Transactional
    public void updateItemById(Long id, ItemUpdateDto itemDto) {
        User user = CommonUtil.getUserFromContextHolder();
        Item item = this.itemRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("itemId", id));

        if (itemDto.getTitle().isBlank()) {
            throw new IsEmptyException("Title");
        }
        if (itemDto.getItemPicture().isBlank()) {
            throw new IsEmptyException("Picture");
        }
        if (itemDto.getDescription().isBlank()) {
            throw new IsEmptyException("Description");
        }

        item.setTitle(itemDto.getTitle());
        item.setItemPicture(itemDto.getItemPicture());
        item.setDescription(itemDto.getDescription());
        item.setCategory(itemDto.getCategory());
        item.setPriceTier(itemDto.getPriceTier());
    }

    @Transactional
    public void updateItemViews(Long id) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        Integer newViews = item.getViews() + 1;
        item.setViews(newViews);
    }

    @Transactional
    public void updateItemReports(Long id) {
        User user = CommonUtil.getUserFromContextHolder();
        Item item = this.itemRepository.findByIdAndUserIdNot(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        List<Long> reports = item.getReports();
        if (reports.contains(user.getId())) {
            throw new IllegalArgumentException("Item already reported");
        }
        reports.add(user.getId());
        item.setReports(reports);
    }

    @Transactional
    public void updateItemState(Long id, String itemState) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        ItemState state = ItemState.findByName(itemState);

        if(state == null) {
            throw new IllegalArgumentException("Given state not exists");
        }

        item.setState(state);

        if(state == ItemState.DISABLED) {
            this.tradeOfferRepository.deleteAllByOfferedItem(item);
            this.tradeOfferRepository.deleteAllByIncomingItem(item);
        }
    }

    //DELETE methods
    public void deleteItemById(Long id) {
        User user = CommonUtil.getUserFromContextHolder();
        Optional<Item> item = this.itemRepository.findByIdAndUserId(id, user);

        if (item.isPresent()) {
            this.itemRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("itemId", id);
        }
    }

    public void deleteAnyItemById(Long id) {
        Optional<Item> item = this.itemRepository.findById(id);

        if (item.isPresent()) {
            this.itemRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("itemId", id);
        }
    }
}
