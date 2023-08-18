package sample.tmp.cryptotrackerapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.tmp.cryptotrackerapi.dto.CryptoCurrencyDTO;
import sample.tmp.cryptotrackerapi.services.CryptoCurrencyService;

import java.util.List;


@RestController
@RequestMapping("/crypto-currencies")
public class CryptoCurrencyController {

    private CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    public CryptoCurrencyController(CryptoCurrencyService cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
    }


    // Метод для получения всех существующих криптовалют
    @GetMapping()
    public ResponseEntity<List<CryptoCurrencyDTO>> getAllCryptoCurrency() {
        List<CryptoCurrencyDTO> cryptoCurrencyDTOS = cryptoCurrencyService.getAllCryptoCurrencies();
        if(!cryptoCurrencyDTOS.isEmpty()) {
            return ResponseEntity.ok(cryptoCurrencyDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Метод для получения криптовалюты по её тегу(3 буквы)
    @GetMapping("/{symbol}")
    public ResponseEntity<CryptoCurrencyDTO> getCryptoCurrencyBySymbol(@PathVariable String symbol) {
        CryptoCurrencyDTO cryptoCurrency = cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol);
        if(cryptoCurrency != null) {
            return ResponseEntity.ok(cryptoCurrency);
        } else {
            return  ResponseEntity.notFound().build();
        }
    }


    // Метод для удаления криптовалюты по её тегу(3 буквы)
    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> deleteCryptoCurrencyBySymbol(@PathVariable String symbol) {
        try {
            cryptoCurrencyService.deleteCryptoCurrencyBySymbol(symbol);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Метод для обновления существующей криптовалюты по её тегу (3 буквы)
    @PutMapping("/{symbol}")
    public ResponseEntity<CryptoCurrencyDTO> updateCryptoCurrencyBySymbol(@PathVariable String symbol, CryptoCurrencyDTO cryptoCurrencyDTO) {
        CryptoCurrencyDTO updateCryptoCurrency = cryptoCurrencyService.updateCryptoCurrencyBySymbol(symbol, cryptoCurrencyDTO);
        if(updateCryptoCurrency != null) {
            return ResponseEntity.ok(updateCryptoCurrency);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Метод для создания новой криптовалюты
    @PostMapping("/create")
    public ResponseEntity<CryptoCurrencyDTO> createCryptoCurrency(@RequestBody CryptoCurrencyDTO cryptoCurrencyDTO) {
        CryptoCurrencyDTO newCryptoCurrencyDTO = cryptoCurrencyService.createCryptoCurrency(cryptoCurrencyDTO);
        return ResponseEntity.ok(newCryptoCurrencyDTO);
    }
}
