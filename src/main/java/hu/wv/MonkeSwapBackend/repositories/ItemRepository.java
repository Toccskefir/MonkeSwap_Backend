package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByState(ItemState state);
    List<Item> findAllByCategoryAndState(ItemCategory category, ItemState state);
    List<Item> findAllByReportsGreaterThanEqual(Integer reports);
}
