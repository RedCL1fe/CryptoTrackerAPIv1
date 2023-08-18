package sample.tmp.cryptotrackerapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.tmp.cryptotrackerapi.dto.PriceHistoryDTO;
import sample.tmp.cryptotrackerapi.services.PriceHistoryService;

import java.util.List;

@RestController
@RequestMapping("/price-history")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @Autowired
    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }



    // Получение всех
    @GetMapping
    public ResponseEntity<List<PriceHistoryDTO>> getAllPriceHistories() {
        List<PriceHistoryDTO> priceHistories = priceHistoryService.getAllPriceHistories();
        if (!priceHistories.isEmpty()) {
            return ResponseEntity.ok(priceHistories);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // Получение конкретной
    @GetMapping("/{symbol}")
    public ResponseEntity<List<PriceHistoryDTO>> getPriceHistoryBySymbol(@PathVariable String symbol) {
        List<PriceHistoryDTO> priceHistories = priceHistoryService.getPriceHistoryBySymbol(symbol);
        if (!priceHistories.isEmpty()) {
            return ResponseEntity.ok(priceHistories);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Создание
    @PostMapping
    public ResponseEntity<PriceHistoryDTO> createPriceHistory(@RequestBody PriceHistoryDTO priceHistoryDTO) {
        PriceHistoryDTO createdPriceHistory = priceHistoryService.createPriceHistory(priceHistoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPriceHistory);
    }



    // Обновление
    @PutMapping("/{id}")
    public ResponseEntity<PriceHistoryDTO> updatePriceHistory(@PathVariable Long id, @RequestBody PriceHistoryDTO priceHistoryDTO) {
        PriceHistoryDTO updatedPriceHistory = priceHistoryService.updatePriceHistory(id, priceHistoryDTO);
        return ResponseEntity.ok(updatedPriceHistory);
    }



    // Удаление
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceHistory(@PathVariable Long id) {
        boolean deleted = priceHistoryService.deletePriceHistory(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
