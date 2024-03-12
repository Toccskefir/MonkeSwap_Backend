package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.model.TradeOffer;
import hu.wv.MonkeSwapBackend.services.TradeOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/tradeoffer")
public class TradeOfferController {
    private final TradeOfferService tradeOfferService;

    @Autowired
    public TradeOfferController(TradeOfferService tradeOfferService) {
        this.tradeOfferService = tradeOfferService;
    }
    @PostMapping
    public void createTradeOffer(@RequestBody TradeOffer tradeOffer) {
        this.tradeOfferService.createTradeOffer(tradeOffer);
    }

    @DeleteMapping("/{tradeOfferId}")
    public void deleteTradeOfferById(@PathVariable("tradeOfferId")Long tradeOfferId) {
        this.tradeOfferService.deleteTradeOfferById(tradeOfferId);
    }
}
