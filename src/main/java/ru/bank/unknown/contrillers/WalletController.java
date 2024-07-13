package ru.bank.unknown.contrillers;

import org.springframework.web.bind.annotation.*;
import ru.bank.unknown.models.Wallet;
import ru.bank.unknown.services.WalletService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @PostMapping
    public Wallet createWallet(@RequestBody Wallet wallet){
        return walletService.createWallet(wallet);
    }
    @GetMapping("/{id}")
    public Wallet getWallet(@PathVariable Long id){
        return walletService.getWallet(id);
    }
    @DeleteMapping("/{id}")
    public Wallet removeWallet(@PathVariable Long id){
        return walletService.removeWallet(id);
    }
    @GetMapping
    public Collection<Wallet> getAllWallet(){
        return walletService.getAllWallet();
    }
    @PutMapping("/put_money")
    public Wallet topUpYourAccount(@RequestParam("id") Long id,
                                   @RequestParam("amount") Long amount){
        return walletService.topUpYourAccount(id, amount);
    }
    @PutMapping("/withdraw")
    public String withdraw(@RequestParam("id") Long id,
                           @RequestParam("amount") Long amount){
        return walletService.withdraw(id, amount);
    }
}
