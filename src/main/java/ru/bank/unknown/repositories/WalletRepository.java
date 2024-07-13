package ru.bank.unknown.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.unknown.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
