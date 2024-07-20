package ru.bank.unknown.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.bank.unknown.exceptions.WalletNotFoundException;
import ru.bank.unknown.models.Wallet;
import ru.bank.unknown.repositories.WalletRepository;

import java.io.Serializable;
import java.lang.annotation.Repeatable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
//    private Atomic

//    private final Object lock = new Object();
//    private volatile Wallet wallet = new Wallet();

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    public Wallet createWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }
    public Wallet getWallet(long id){
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("В базе нет этого счета"));
    }
    public Wallet removeWallet(long id){
        Wallet removeWallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("В базе нет этого счета"));
        walletRepository.delete(removeWallet);
        return removeWallet;
    }
    public Collection<Wallet> getAllWallet(){
        return Collections.unmodifiableCollection(walletRepository.findAll());
    }

    @Transactional
//    @Retryable
    public Wallet topUpYourAccount(long id, long amount){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("В базе нет этого счета"));
//        synchronized (lock){
//            wallet.setAmount(wallet.getAmount() + amount);
//            walletRepository.save(wallet);
//            return wallet;
//        }
        wallet.setAmount(wallet.getAmount() + amount);
        this.walletRepository.save(wallet);
        return wallet;
    }
    @Transactional
    public String withdraw(long id, long amount){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("В базе нет этого счета"));
        if (wallet.getAmount() < amount){
            return "Недостаточно стредств";
        }
//        synchronized (lock){
//            wallet.setAmount(wallet.getAmount() - amount);
//            walletRepository.save(wallet);
//            return "Операция проведена успешно";
        wallet.setAmount(wallet.getAmount() - amount);
        walletRepository.save(wallet);
        return "Операция проведена успешно";
    }
}
