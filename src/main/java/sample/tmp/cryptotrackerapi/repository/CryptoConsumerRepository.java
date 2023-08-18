package sample.tmp.cryptotrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.tmp.cryptotrackerapi.models.CryptoСonsumer;

import java.util.Optional;

@Repository
public interface CryptoConsumerRepository extends JpaRepository<CryptoСonsumer, Long> {

    //поиск по именя для СС
    Optional<CryptoСonsumer> findByUsername(String username);
}
