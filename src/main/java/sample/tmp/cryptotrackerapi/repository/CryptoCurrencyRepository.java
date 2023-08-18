package sample.tmp.cryptotrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.tmp.cryptotrackerapi.models.CryptoCurrency;

import java.util.Optional;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {

    // Метод для поиска криптовалюты по ее тэгу(3 буквам)
    Optional<CryptoCurrency> findBySymbol(String symbol);

    // Метод для удаления криптовалюты по ее тэгу(3 буквы)
    void deleteBySymbol(String symbol);

}
