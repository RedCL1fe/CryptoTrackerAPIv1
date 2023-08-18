package sample.tmp.cryptotrackerapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.tmp.cryptotrackerapi.dto.CryptoCurrencyInfo;
import sample.tmp.cryptotrackerapi.dto.PriceHistoryDTO;
import sample.tmp.cryptotrackerapi.models.CryptoCurrency;
import sample.tmp.cryptotrackerapi.models.PriceHistory;
import sample.tmp.cryptotrackerapi.repository.CryptoCurrencyRepository;
import sample.tmp.cryptotrackerapi.repository.PriceHistoryRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CoinGeckoApiClient coinGeckoApiClient;

    @Autowired
    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository, CryptoCurrencyRepository cryptoCurrencyRepository,
                               CoinGeckoApiClient coinGeckoApiClient) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.coinGeckoApiClient = coinGeckoApiClient;
    }


    //Получение кокретной цены
    public List<PriceHistoryDTO> getPriceHistoryBySymbol(String symbol) {
        List<PriceHistory> priceHistories = priceHistoryRepository.findByCryptoCurrency_SymbolOrderByDateDesc(symbol);
        return priceHistories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // Получения всех цен
    public List<PriceHistoryDTO> getAllPriceHistories() {
        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        return priceHistories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Создание
    public PriceHistoryDTO createPriceHistory(PriceHistoryDTO priceHistoryDTO) {
        PriceHistory priceHistory = convertToEntity(priceHistoryDTO);
        PriceHistory savedPriceHistory = priceHistoryRepository.save(priceHistory);
        return convertToDTO(savedPriceHistory);
    }


    // Обновление
    public PriceHistoryDTO updatePriceHistory(Long id, PriceHistoryDTO priceHistoryDTO) {
        PriceHistory existingPriceHistory = priceHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceHistory not found with id: " + id));

        existingPriceHistory.setPrice(priceHistoryDTO.getPrice());
        existingPriceHistory.setDate(priceHistoryDTO.getDate());

        existingPriceHistory = priceHistoryRepository.save(existingPriceHistory);
        return convertToDTO(existingPriceHistory);
    }


    // Удаление
    public boolean deletePriceHistory(Long id) {
        Optional<PriceHistory> optionalPriceHistory = priceHistoryRepository.findById(id);
        if (optionalPriceHistory.isPresent()) {
            priceHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }


    //
    public BigDecimal getCurrentPrice(String cryptoSymbol) {
        // Получите информацию о криптовалюте
        CryptoCurrencyInfo cryptoCurrencyInfo = coinGeckoApiClient.getCryptoCurrencyInfo(cryptoSymbol);

        // Извлеките текущую цену из объекта CryptoCurrencyInfo
        BigDecimal currentPrice = cryptoCurrencyInfo.getCurrentPrice();

        return currentPrice;
    }

    // Два метода ниже технические для конвертации обьектов и ДТО
    private PriceHistoryDTO convertToDTO(PriceHistory priceHistory) {
        PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO();
        priceHistoryDTO.setId(priceHistory.getId());
        priceHistoryDTO.setPrice(priceHistory.getPrice());
        priceHistoryDTO.setDate(priceHistory.getDate());
        return priceHistoryDTO;
    }


    private PriceHistory convertToEntity(PriceHistoryDTO priceHistoryDTO) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setId(priceHistoryDTO.getId());
        priceHistory.setPrice(priceHistoryDTO.getPrice());
        priceHistory.setDate(priceHistoryDTO.getDate());

        Optional<CryptoCurrency> optionalCryptoCurrency = cryptoCurrencyRepository.findBySymbol(priceHistoryDTO.getCryptoCurrencySymbol());
        if (optionalCryptoCurrency.isPresent()) {
            priceHistory.setCryptoCurrency(optionalCryptoCurrency.get());
        } else {
            throw new RuntimeException("CryptoCurrency not found with symbol: " + priceHistoryDTO.getCryptoCurrencySymbol());
        }

        return priceHistory;
    }

}
