package hu.wv.MonkeSwapBackend.utils;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommonUtil {
    private UserRepository userRepository;

    public User getUserFromContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("Anonymous request");
        } else {
            String currentPrincipalName = authentication.getName();
            return userRepository.findByEmail(currentPrincipalName).get();
        }
    }

    public List<ItemDto> convertItemListToItemDtoList(List<Item> list) {
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
}
