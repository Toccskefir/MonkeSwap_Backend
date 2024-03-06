package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.model.TradeOffer;
import hu.wv.MonkeSwapBackend.repositories.TradeOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeOfferService {
    private final TradeOfferRepository tradeOfferRepository;

    @Autowired
    public TradeOfferService(TradeOfferRepository tradeOfferRepository) {
        this.tradeOfferRepository = tradeOfferRepository;
    }

    @Transactional
    public void createTradeOffer(TradeOffer tradeOffer) {
        if (this.tradeOfferRepository.findTradeOfferByOfferedItemAndIncomingItem(
                    tradeOffer.getOfferedItem(),
                    tradeOffer.getIncomingItem()).isPresent()){
            throw new IllegalArgumentException("Trade offer already sent");
        }
        if (this.tradeOfferRepository.findTradeOfferByOfferedItemAndIncomingItem(
                    tradeOffer.getIncomingItem(),
                    tradeOffer.getOfferedItem()).isPresent()) {
            throw new IllegalArgumentException("Trade offer already received");
        }

        this.tradeOfferRepository.save(tradeOffer);
    }
}
