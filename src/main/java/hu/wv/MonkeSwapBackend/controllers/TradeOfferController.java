package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.TradeOfferDto;
import hu.wv.MonkeSwapBackend.services.TradeOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tradeoffer")
public class TradeOfferController {
    private final TradeOfferService tradeOfferService;

    @Autowired
    public TradeOfferController(TradeOfferService tradeOfferService) {
        this.tradeOfferService = tradeOfferService;
    }

    //GET methods
    @GetMapping("/offered")
    public List<TradeOfferDto> getOfferedTradeOffers() {
        return this.tradeOfferService.getOfferedTradeOffers();
    }

    @GetMapping("/incoming")
    public List<TradeOfferDto> getIncomingTradeOffers() {
        return this.tradeOfferService.getIncomingTradeOffers();
    }

    //POST methods
    @PostMapping
    public void createTradeOffer(@RequestBody TradeOfferDto tradeOffer) {
        this.tradeOfferService.createTradeOffer(tradeOffer);
    }

    //DELETE methods
    @DeleteMapping("/{tradeOfferId}")
    public void deleteTradeOfferById(@PathVariable("tradeOfferId")Long tradeOfferId) {
        this.tradeOfferService.deleteTradeOfferById(tradeOfferId);
    }
}
