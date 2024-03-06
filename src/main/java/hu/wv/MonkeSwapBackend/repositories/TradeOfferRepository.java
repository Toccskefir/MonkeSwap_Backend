package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long> {
    Optional<TradeOffer> findTradeOfferByOfferedItemAndIncomingItem(Item offeredItem, Item incomingItem);
}
