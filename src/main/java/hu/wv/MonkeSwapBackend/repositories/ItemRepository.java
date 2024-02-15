package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
