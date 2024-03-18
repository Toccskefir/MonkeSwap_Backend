package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long> {
    Optional<TradeOffer> findByOfferedItemAndIncomingItem(Item offeredItem, Item incomingItem);
    List<TradeOffer> findAllByOfferedItem(Item offeredItem);
    List<TradeOffer> findAllByIncomingItem(Item incomingItem);
    void deleteAllByOfferedItem(Item offeredItem);
    void deleteAllByIncomingItem(Item incomingItem);
}
