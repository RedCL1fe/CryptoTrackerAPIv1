package sample.tmp.cryptotrackerapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.tmp.cryptotrackerapi.dto.CryptoConsumerDTO;
import sample.tmp.cryptotrackerapi.services.CryptoConsumerService;

import java.util.List;

@RestController
@RequestMapping("/consumers")
public class CryptoConsumerController {

    private CryptoConsumerService cryptoConsumerService;

    @Autowired
    public CryptoConsumerController(CryptoConsumerService cryptoConsumerService) {
        this.cryptoConsumerService = cryptoConsumerService;
    }

    // Метод для получения информации о пользователе по идентификатору
    @GetMapping("/{id}")
    public ResponseEntity<CryptoConsumerDTO> getConsumerById(@PathVariable("id") Long id) {
        CryptoConsumerDTO cryptoConsumerDTO = cryptoConsumerService.getConsumerById(id);
        if(cryptoConsumerDTO != null) return ResponseEntity.ok(cryptoConsumerDTO);

        return ResponseEntity.notFound().build();
    }


    // Метод для получения информации о всех существующих пользователях
    @GetMapping()
    public ResponseEntity<List<CryptoConsumerDTO>> getAllConsumers() {
        List<CryptoConsumerDTO> cryptoConsumerDTOList = cryptoConsumerService.getAllConsumers();
        return ResponseEntity.ok(cryptoConsumerDTOList);
    }


    // Метод для обновления полей конкретного пользователя
    @PutMapping("/{id}")
    public ResponseEntity<CryptoConsumerDTO> updateConsumer(@PathVariable("id") Long id, @RequestBody CryptoConsumerDTO cryptoConsumerDTO) {
        try {
            CryptoConsumerDTO updatedCryptoConsumerDTO = cryptoConsumerService.updateConsumer(id, cryptoConsumerDTO);
            return ResponseEntity.ok(updatedCryptoConsumerDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Метод для создания нового пользователя
    @PostMapping("/create")
    public ResponseEntity<CryptoConsumerDTO> createConsumer(@RequestBody CryptoConsumerDTO cryptoConsumerDTO) {
        CryptoConsumerDTO newCryptoConsumerDTO = cryptoConsumerService.createConsumer(cryptoConsumerDTO);
        return ResponseEntity.ok(newCryptoConsumerDTO);
    }


    // Метод для удаления конкретного пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable("id") Long id) {
        try {
            cryptoConsumerService.deleteConsumer(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
