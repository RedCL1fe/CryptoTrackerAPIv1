package sample.tmp.cryptotrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.tmp.cryptotrackerapi.dto.PriceHistoryDTO;
import sample.tmp.cryptotrackerapi.models.PriceHistory;

import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    List<PriceHistory> findByCryptoCurrency_SymbolOrderByDateDesc(String symbol);
}
