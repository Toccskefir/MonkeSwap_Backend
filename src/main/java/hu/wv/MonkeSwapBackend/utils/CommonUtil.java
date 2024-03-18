package hu.wv.MonkeSwapBackend.utils;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.dtos.NotificationDto;
import hu.wv.MonkeSwapBackend.dtos.UserDto;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.Notification;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonUtil {
    private static UserRepository userRepository;

    @Autowired
    public CommonUtil(UserRepository userRepository) {
        CommonUtil.userRepository = userRepository;
    }

    public static User getUserFromContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("Anonymous request");
        } else {
            String currentPrincipalName = authentication.getName();
            return userRepository.findByEmail(currentPrincipalName).get();
        }
    }

    public static UserDto convertUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getRealUsername())
                .tradesCompleted(user.getTradesCompleted())
                .role(user.getRole())
                .dateOfRegistration(user.getDateOfRegistration())
                .fullName(user.getFullName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(user.getProfilePicture())
                .build();
    }

    public static List<NotificationDto> convertNotificationToNotificationDto(List<Notification> list) {
        List<NotificationDto> listToReturn = new ArrayList<>();
        list.forEach(item -> {
            NotificationDto dto = NotificationDto.builder()
                    .id(item.getId())
                    .message(item.getMessage())
                    .type(item.getType())
                    .build();
            listToReturn.add(dto);
        });
        return listToReturn;
    }

    public static ItemDto convertItemToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .itemPicture(item.getItemPicture())
                .description(item.getDescription())
                .views(item.getViews())
                .reports(item.getReports())
                .state(item.getState())
                .category(item.getCategory())
                .priceTier(item.getPriceTier())
                .userId(item.getUserId().getId())
                .build();
    }

    public static List<ItemDto> convertItemListToItemDtoList(List<Item> list) {
        List<ItemDto> listToReturn = new ArrayList<>();
        list.forEach(item -> {
            ItemDto dto = ItemDto.builder()
                    .id(item.getId())
                    .title(item.getTitle())
                    .itemPicture(item.getItemPicture())
                    .description(item.getDescription())
                    .views(item.getViews())
                    .reports(item.getReports())
                    .state(item.getState())
                    .category(item.getCategory())
                    .priceTier(item.getPriceTier())
                    .userId(item.getUserId().getId())
                    .build();
            listToReturn.add(dto);
        });
        return listToReturn;
    }
}
