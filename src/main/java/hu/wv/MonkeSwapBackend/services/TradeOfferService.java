package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.TradeOfferDto;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.TradeOffer;
import hu.wv.MonkeSwapBackend.repositories.ItemRepository;
import hu.wv.MonkeSwapBackend.repositories.TradeOfferRepository;
import hu.wv.MonkeSwapBackend.utils.CommonUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TradeOfferService {
    private final TradeOfferRepository tradeOfferRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public TradeOfferService(TradeOfferRepository tradeOfferRepository, ItemRepository itemRepository) {
        this.tradeOfferRepository = tradeOfferRepository;
        this.itemRepository = itemRepository;
    }

    private TradeOfferDto convertTradeOfferToTradeOfferDto(TradeOffer item) {
        return TradeOfferDto.builder()
                .id(item.getId())
                .offeredItem(item.getOfferedItem())
                .incomingItem(item.getIncomingItem())
                .comment(item.getComment())
                .build();
    }

    public List<TradeOfferDto> getOfferedTradeOffers() {
        List<Item> items = itemRepository
                .findAllByUserIdAndState(CommonUtil.getUserFromContextHolder(), ItemState.ENABLED);
        List<TradeOfferDto> offeredTradeOffers = new ArrayList<>();
        items.forEach(item -> {
            Optional<TradeOffer> tradeOffer = this.tradeOfferRepository.findTradeOfferByOfferedItem(item);
            tradeOffer.ifPresent(offer -> offeredTradeOffers.add(this.convertTradeOfferToTradeOfferDto(offer)));
        });
        return offeredTradeOffers;
    }

    public List<TradeOfferDto> getIncomingTradeOffers() {
        List<Item> items = itemRepository
                .findAllByUserIdAndState(CommonUtil.getUserFromContextHolder(), ItemState.ENABLED);
        List<TradeOfferDto> incomingTradeOffers = new ArrayList<>();
        items.forEach(item -> {
            Optional<TradeOffer> tradeOffer = this.tradeOfferRepository.findTradeOfferByIncomingItem(item);
            tradeOffer.ifPresent(offer -> incomingTradeOffers.add(this.convertTradeOfferToTradeOfferDto(offer)));
        });
        return incomingTradeOffers;
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
