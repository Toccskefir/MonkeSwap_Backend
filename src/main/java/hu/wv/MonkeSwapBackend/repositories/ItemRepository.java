package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
