package sample.tmp.cryptotrackerapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.tmp.cryptotrackerapi.dto.CryptoConsumerDTO;
import sample.tmp.cryptotrackerapi.models.CryptoСonsumer;
import sample.tmp.cryptotrackerapi.services.CryptoConsumerService;

@RestController
public class AuthController {

    private final CryptoConsumerService cryptoConsumerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(CryptoConsumerService cryptoConsumerService, PasswordEncoder passwordEncoder) {
        this.cryptoConsumerService = cryptoConsumerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody CryptoСonsumer newUser) {
        // Проверка наличия пользователя с таким именем
        if (cryptoConsumerService.findByUsername(newUser.getUsername()) != null) {
            return "User with this username already exists";
        }

        // Создание нового пользователя
        CryptoConsumerDTO user = new CryptoConsumerDTO();
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword())); // Зашифрованный пароль

        // Сохранение пользователя в базе данных
        cryptoConsumerService.saveConsumer(user);

        return "User registered successfully";
    }
}