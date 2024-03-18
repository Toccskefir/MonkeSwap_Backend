package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.TradeOfferDto;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.TradeOffer;
import hu.wv.MonkeSwapBackend.repositories.ItemRepository;
import hu.wv.MonkeSwapBackend.repositories.TradeOfferRepository;
import hu.wv.MonkeSwapBackend.utils.CommonUtil;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeOfferService {
    private final TradeOfferRepository tradeOfferRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public TradeOfferService(TradeOfferRepository tradeOfferRepository, ItemRepository itemRepository) {
        this.tradeOfferRepository = tradeOfferRepository;
        this.itemRepository = itemRepository;
    }

    //READ methods
    public List<TradeOfferDto> getOfferedTradeOffers() {
        List<Item> items = this.itemRepository
                .findAllByUserIdAndState(CommonUtil.getUserFromContextHolder(), ItemState.ENABLED);
        List<TradeOfferDto> offeredTradeOffers = new ArrayList<>();
        items.forEach(item -> {
            List<TradeOffer> tradeOffers = this.tradeOfferRepository.findAllByOfferedItem(item);
            tradeOffers.forEach(offer -> offeredTradeOffers.add(CommonUtil.convertTradeOfferToTradeOfferDto(offer)));
        });
        return offeredTradeOffers;
    }

    public List<TradeOfferDto> getIncomingTradeOffers() {
        List<Item> items = this.itemRepository
                .findAllByUserIdAndState(CommonUtil.getUserFromContextHolder(), ItemState.ENABLED);
        List<TradeOfferDto> incomingTradeOffers = new ArrayList<>();
        items.forEach(item -> {
            List<TradeOffer> tradeOffer = this.tradeOfferRepository.findAllByIncomingItem(item);
            tradeOffer.forEach(offer -> incomingTradeOffers.add(CommonUtil.convertTradeOfferToTradeOfferDto(offer)));
        });
        return incomingTradeOffers;
    }

    //CREATE methods
    @Transactional
    public void createTradeOffer(TradeOffer tradeOffer) {
        if (this.tradeOfferRepository.findByOfferedItemAndIncomingItem(
                    tradeOffer.getOfferedItem(),
                    tradeOffer.getIncomingItem()).isPresent()){
            throw new IllegalArgumentException("Trade offer already sent");
        }
        if (this.tradeOfferRepository.findByOfferedItemAndIncomingItem(
                    tradeOffer.getIncomingItem(),
                    tradeOffer.getOfferedItem()).isPresent()) {
            throw new IllegalArgumentException("Trade offer already received");
        }

        this.tradeOfferRepository.save(tradeOffer);
    }

    //DELETE methods
    @Transactional
    public void deleteTradeOfferById(Long id) {
        this.tradeOfferRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("tradeOfferId", id));
        this.tradeOfferRepository.deleteById(id);
    }
}
