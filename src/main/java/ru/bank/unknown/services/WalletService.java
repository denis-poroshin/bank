package ru.bank.unknown.services;

import org.springframework.stereotype.Service;
import ru.bank.unknown.exceptions.WalletNotFoundException;
import ru.bank.unknown.models.Wallet;
import ru.bank.unknown.repositories.WalletRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

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
    public Wallet topUpYourAccount(long id, long amount){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("В базе нет этого счета"));
        wallet.setAmount(wallet.getAmount() + amount);
        walletRepository.save(wallet);
        return wallet;
    }
    public String withdraw(long id, long amount){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("В базе нет этого счета"));
        if (wallet.getAmount() < amount){
            return "Недостаточно стредств";
        }
        wallet.setAmount(wallet.getAmount() - amount);
        walletRepository.save(wallet);
        return "Операция проведена успешно";
    }
}
