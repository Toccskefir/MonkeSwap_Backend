package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByStateAndUserIdNot(ItemState state, User user);
    List<Item> findAllByCategoryAndStateAndUserIdNot(ItemCategory category, ItemState state, User user);
    List<Item> findAllByUserIdAndState(User user, ItemState state);
    List<Item> findAllByUserId(User user);
    List<Item> findAllByReportsGreaterThanEqual(Integer reports);
    Optional<Item> findByIdAndReportsGreaterThan(Long id, Integer reports);
}
