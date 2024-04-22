package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.ItemCreateDto;
import hu.wv.MonkeSwapBackend.dtos.ItemDto;
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
import java.util.List;

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
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("userId", userId));
        List<Item> items = this.itemRepository.findAllByUserIdAndState(user, ItemState.ENABLED);
        return CommonUtil.convertItemListToItemDtoList(items);
    }

    public List<ItemDto> getLoggedInUserItems() {
        List<Item> items = this.itemRepository
                .findAllByUserId(CommonUtil.getUserFromContextHolder());
        return CommonUtil.convertItemListToItemDtoList(items);
    }

    public ItemDto getItemById(Long id) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        return CommonUtil.convertItemToItemDto(item);
    }

    public ItemDto getReportedItemById(Long id) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        if (item.getReports().size() < 5) {
            throw new ObjectNotFoundException("itemId", id);
        }
        return CommonUtil.convertItemToItemDto(item);
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
    public void createItem(ItemCreateDto itemDto) {
        ItemCategory category = ItemCategory.findByName(itemDto.getCategory());

        if (itemDto.getItemPicture() == null) {
            throw new IsEmptyException("Picture");
        }
        if (itemDto.getTitle().isBlank()) {
            throw new IsEmptyException("Title");
        }
        if (itemDto.getDescription().isBlank()) {
            throw new IsEmptyException("Description");
        }
        if (category == null) {
            throw new IllegalArgumentException("Given category not exists");
        }

        Item item = Item.builder()
                .title(itemDto.getTitle())
                .itemPicture(itemDto.getItemPicture())
                .description(itemDto.getDescription())
                .views(0)
                .state(ItemState.ENABLED)
                .category(category)
                .priceTier(itemDto.getPriceTier())
                .userId(CommonUtil.getUserFromContextHolder())
                .build();

        this.itemRepository.save(item);
    }

    //UPDATE methods
    @Transactional
    public ItemDto updateItemById(Long id, ItemCreateDto itemDto) {
        ItemCategory category = ItemCategory.findByName(itemDto.getCategory());
        User user = CommonUtil.getUserFromContextHolder();
        Item item = this.itemRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("itemId", id));

        if (itemDto.getItemPicture() == null) {
            throw new IsEmptyException("Picture");
        }
        if (itemDto.getTitle().isBlank()) {
            throw new IsEmptyException("Title");
        }
        if (itemDto.getDescription().isBlank()) {
            throw new IsEmptyException("Description");
        }
        if (category == null) {
            throw new IllegalArgumentException("Given category not exists");
        }

        item.setTitle(itemDto.getTitle());
        item.setItemPicture(itemDto.getItemPicture());
        item.setDescription(itemDto.getDescription());
        item.setCategory(category);
        item.setPriceTier(itemDto.getPriceTier());
        return CommonUtil.convertItemToItemDto(this.itemRepository.save(item));
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
        this.itemRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        this.itemRepository.deleteById(id);
    }

    public void deleteAnyItemById(Long id) {
        this.itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("itemId", id));
        this.itemRepository.deleteById(id);
    }
}
