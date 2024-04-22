package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.TradeOfferDto;
import hu.wv.MonkeSwapBackend.services.TradeOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Returns a list of offered trade offers of the logged-in user")
    @GetMapping("/offered")
    public List<TradeOfferDto> getOfferedTradeOffers() {
        return this.tradeOfferService.getOfferedTradeOffers();
    }

    @Operation(summary = "Returns a list of incoming trade offers of the logged-in user")
    @GetMapping("/incoming")
    public List<TradeOfferDto> getIncomingTradeOffers() {
        return this.tradeOfferService.getIncomingTradeOffers();
    }

    //POST methods
    @Operation(summary = "Creates a trade offer with the given item ids")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Trade offer successfully sent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Trade offer already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "Items not found", content = @Content)
    })
    @PostMapping
    public void createTradeOffer(@RequestBody TradeOfferDto tradeOffer) {
        this.tradeOfferService.createTradeOffer(tradeOffer);
    }

    //DELETE methods
    @Operation(summary = "Deletes a trade offer by id when declined")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Trade offer successfully declined", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trade offer not found", content = @Content)
    })
    @DeleteMapping("/decline/{tradeOfferId}")
    public void deleteTradeOfferByIdOnDecline(@PathVariable("tradeOfferId")Long tradeOfferId) {
        this.tradeOfferService.deleteTradeOfferByIdOnDecline(tradeOfferId);
    }

    @Operation(summary = "Deletes a trade offer by id and its items when accepted")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Trade offer successfully accepted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trade offer not found", content = @Content)
    })
    @DeleteMapping("/accept/{tradeOfferId}")
    public void deleteTradeOfferByIdOnAccept(@PathVariable("tradeOfferId")Long tradeOfferId) {
        this.tradeOfferService.deleteTradeOfferByIdOnAccept(tradeOfferId);
    }
}
