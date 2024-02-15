package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long> {

}
