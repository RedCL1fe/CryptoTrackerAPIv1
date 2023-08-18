package sample.tmp.cryptotrackerapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.tmp.cryptotrackerapi.dto.CryptoCurrencyDTO;
import sample.tmp.cryptotrackerapi.models.CryptoCurrency;
import sample.tmp.cryptotrackerapi.repository.CryptoCurrencyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoCurrencyService {

    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }


    // Метод для получения по тэгу (3 буквы)
    public CryptoCurrencyDTO getCryptoCurrencyBySymbol(String symbol) {
        Optional<CryptoCurrency> optionalCryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol);
        return optionalCryptoCurrency.map(this::convertToDTO).orElse(null);
    }


    // Метод для получения всех доступных критовалют
    public List<CryptoCurrencyDTO> getAllCryptoCurrencies() {
        List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyRepository.findAll();
        return cryptoCurrencies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // Метод для удаления криптовалюты по ее тэгу(3 буквы)
    public void deleteCryptoCurrencyBySymbol(String symbol) {
        try {
            cryptoCurrencyRepository.deleteBySymbol(symbol);
            cryptoCurrencyRepository.flush();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting crypto currency");
        }
    }


    // Метод для обновления информации о существующей криптовалюте
    public CryptoCurrencyDTO updateCryptoCurrencyBySymbol(String symbol, CryptoCurrencyDTO cryptoCurrencyDTO) {
        Optional<CryptoCurrency> optionalCryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol);
        if (optionalCryptoCurrency.isPresent()) {
            CryptoCurrency existingCryptoCurrency = optionalCryptoCurrency.get();
            existingCryptoCurrency.setName(cryptoCurrencyDTO.getName());
            existingCryptoCurrency.setSymbol(cryptoCurrencyDTO.getSymbol());
            existingCryptoCurrency.setCurrentPrice(cryptoCurrencyDTO.getCurrentPrice());
            CryptoCurrency updatedCryptoCurrency = cryptoCurrencyRepository.save(existingCryptoCurrency);
            return convertToDTO(updatedCryptoCurrency);
        }
        return null;
    }


    // Метод для добавления новой криптовалюты
    public CryptoCurrencyDTO createCryptoCurrency(CryptoCurrencyDTO cryptoCurrencyDTO) {
        CryptoCurrency cryptoCurrency = convertToEntity(cryptoCurrencyDTO);
        CryptoCurrency savedCryptoCurrency = cryptoCurrencyRepository.save(cryptoCurrency);
        return convertToDTO(savedCryptoCurrency);
    }


    // Метод конвертации обычного обьекта в DTO
    private CryptoCurrencyDTO convertToDTO(CryptoCurrency cryptoCurrency) {
        return new CryptoCurrencyDTO(
                cryptoCurrency.getId(),
                cryptoCurrency.getName(),
                cryptoCurrency.getSymbol(),
                cryptoCurrency.getCurrentPrice()
        );
    }


    // Метод конвертации DTO в обычный обьект
    private CryptoCurrency convertToEntity(CryptoCurrencyDTO cryptoCurrencyDTO) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setName(cryptoCurrencyDTO.getName());
        cryptoCurrency.setSymbol(cryptoCurrencyDTO.getSymbol());
        cryptoCurrency.setCurrentPrice(cryptoCurrencyDTO.getCurrentPrice());
        return cryptoCurrency;
    }
}