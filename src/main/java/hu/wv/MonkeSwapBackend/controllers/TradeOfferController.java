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
    //returns a list of offered trade offers of the logged-in user
    @GetMapping("/offered")
    public List<TradeOfferDto> getOfferedTradeOffers() {
        return this.tradeOfferService.getOfferedTradeOffers();
    }

    //returns a list of incoming trade offers of the logged-in user
    @GetMapping("/incoming")
    public List<TradeOfferDto> getIncomingTradeOffers() {
        return this.tradeOfferService.getIncomingTradeOffers();
    }

    //POST methods
    //creates a trade offer with the given item ids
    @PostMapping
    public void createTradeOffer(@RequestBody TradeOfferDto tradeOffer) {
        this.tradeOfferService.createTradeOffer(tradeOffer);
    }

    //DELETE methods
    //deletes a trade offer by id when declined
    @DeleteMapping("/decline/{tradeOfferId}")
    public void deleteTradeOfferByIdOnDecline(@PathVariable("tradeOfferId")Long tradeOfferId) {
        this.tradeOfferService.deleteTradeOfferByIdOnDecline(tradeOfferId);
    }

    //deletes a trade offer by id and its items when accepted
    @DeleteMapping("/accept/{tradeOfferId}")
    public void deleteTradeOfferByIdOnAccept(@PathVariable("tradeOfferId")Long tradeOfferId) {
        this.tradeOfferService.deleteTradeOfferByIdOnAccept(tradeOfferId);
    }
}
