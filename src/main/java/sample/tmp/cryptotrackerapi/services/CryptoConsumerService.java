package sample.tmp.cryptotrackerapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sample.tmp.cryptotrackerapi.dto.CryptoConsumerDTO;
import sample.tmp.cryptotrackerapi.models.*;
import sample.tmp.cryptotrackerapi.repository.CryptoConsumerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CryptoConsumerService implements UserDetailsService {

    private CryptoConsumerRepository cryptoConsumerRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public CryptoConsumerService(CryptoConsumerRepository cryptoConsumerRepository,
                                 UserDetailsService userDetailsService,
                                 PasswordEncoder passwordEncoder) {
        this.cryptoConsumerRepository = cryptoConsumerRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    // Переопределение метода СС для авторизации
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CryptoСonsumer user = cryptoConsumerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с именем пользователя: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // Здесь можно добавить роли пользователя, если необходимо
        );
    }


    // Метод для получения пользователя по индефикатору
    public CryptoConsumerDTO getConsumerById(Long id) {
        CryptoСonsumer cryptoСonsumer = cryptoConsumerRepository.findById(id).orElse(null);
        return convertToCryptoConsumerDTO(cryptoСonsumer);
    }


    // Метод для получения всех пользователей
    public List<CryptoConsumerDTO> getAllConsumers() {
        List<CryptoСonsumer> cryptoСonsumers = cryptoConsumerRepository.findAll();
        return convertToCryptoConsumerDTOList(cryptoСonsumers);
    }


    // Метод для поиска пользователя по имени
    public Optional<CryptoConsumerDTO> findByUsername(String username) {
        Optional<CryptoСonsumer> optionalCryptoConsumer = cryptoConsumerRepository.findByUsername(username);
        return optionalCryptoConsumer.map(this::convertToCryptoConsumerDTO);
    }



    // Метод создания нового пользователя
    public CryptoConsumerDTO createConsumer(CryptoConsumerDTO cryptoConsumerDTO) {
        CryptoСonsumer newCryptoConsumer = convertToCryptoConsumer(cryptoConsumerDTO);
        newCryptoConsumer = cryptoConsumerRepository.save(newCryptoConsumer);
        return convertToCryptoConsumerDTO(newCryptoConsumer);
    }


    // Метод обновления данных в существующем пользователе
    public CryptoConsumerDTO updateConsumer(Long id, CryptoConsumerDTO cryptoConsumerDTO) {
        Optional<CryptoСonsumer> optionalCryptoСonsumer = cryptoConsumerRepository.findById(id);
        if (optionalCryptoСonsumer.isPresent()) {
            CryptoСonsumer existingCryptoСonsumer = optionalCryptoСonsumer.get();
            updateCryptoConsumerData(existingCryptoСonsumer, cryptoConsumerDTO);
            existingCryptoСonsumer = cryptoConsumerRepository.save(existingCryptoСonsumer);
            return convertToCryptoConsumerDTO(existingCryptoСonsumer);
        }
        throw new RuntimeException("Not found consumer, sorry :(");
    }


    // Удаление
    public void deleteConsumer(Long id) {
        Optional<CryptoСonsumer> optionalCryptoСonsumer = cryptoConsumerRepository.findById(id);
        if (optionalCryptoСonsumer.isPresent()) {
            cryptoConsumerRepository.deleteById(id);
        }
    }


    // Вспомогательный метод для преобразования CryptoConsumer в CryptoConsumerDTO
    private CryptoConsumerDTO convertToCryptoConsumerDTO(CryptoСonsumer cryptoСonsumer) {
        return new CryptoConsumerDTO(
        );
    }


    // Вспомогательный метод для преобразования CryptoConsumerDTO в CryptoConsumer
    private List<CryptoConsumerDTO> convertToCryptoConsumerDTOList(List<CryptoСonsumer> cryptoСonsumers) {
        return cryptoСonsumers.stream()
                .map(this::convertToCryptoConsumerDTO)
                .collect(Collectors.toList());
    }


    // Преобразования информации из DTO в сам обьект
    private CryptoСonsumer convertToCryptoConsumer(CryptoConsumerDTO cryptoConsumerDTO) {
        return new CryptoСonsumer(cryptoConsumerDTO.getUsername(), cryptoConsumerDTO.getEmail());
    }


    // Обновление информации в самом обьекте по средством получения данных из DTO обьекта
    private void updateCryptoConsumerData(CryptoСonsumer cryptoСonsumer, CryptoConsumerDTO cryptoConsumerDTO) {
        cryptoСonsumer.setUsername(cryptoConsumerDTO.getUsername());
        cryptoСonsumer.setEmail(cryptoConsumerDTO.getEmail());
    }

    // Метод для сохранения пользователя
    public CryptoConsumerDTO saveConsumer(CryptoConsumerDTO cryptoConsumerDTO) {
        // Конвертируем DTO в сущность CryptoСonsumer
        CryptoСonsumer newCryptoConsumer = convertToCryptoConsumer(cryptoConsumerDTO);

        // Шифруем пароль
        String encodedPassword = passwordEncoder.encode(cryptoConsumerDTO.getPassword());
        newCryptoConsumer.setPassword(encodedPassword);

        // Сохраняем пользователя
        newCryptoConsumer = cryptoConsumerRepository.save(newCryptoConsumer);

        // Конвертируем обратно в DTO и возвращаем
        return convertToCryptoConsumerDTO(newCryptoConsumer);
    }


}
