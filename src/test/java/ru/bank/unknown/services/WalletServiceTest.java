package ru.bank.unknown.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.events.Event;
import ru.bank.unknown.exceptions.WalletNotFoundException;
import ru.bank.unknown.models.Wallet;
import ru.bank.unknown.repositories.WalletRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepositoryMock;
    @InjectMocks
    private WalletService walletServiceTest;
    private final Wallet walletOne = new Wallet(1L, 1000L);
    private final Wallet walletTwo = new Wallet(2L, 2000L);
    private final Wallet walletTree = new Wallet(3L, 3000L);

    List<Wallet> wallets = new ArrayList<>(List.of(walletOne, walletTwo, walletTree));
    private static final Long ID = 1L;
    @Test
    public void createWalletTest(){
        Wallet actual = new Wallet(4L, 4000L);
        List<Wallet> walletsNew = new ArrayList<>(List.of(walletOne, walletTwo, walletTree, actual));
        Mockito.when(walletRepositoryMock.save(actual)).thenReturn(actual);
        Mockito.when(walletRepositoryMock.findAll()).thenReturn(walletsNew);


        Assertions.assertEquals(walletServiceTest.createWallet(actual), actual);
        assertArrayEquals(walletServiceTest.getAllWallet().toArray(), walletsNew.toArray());
        walletServiceTest.createWallet(walletOne);
        verify(walletRepositoryMock).save(walletOne);
    }
    @Test
    public void getWalletTest(){
        Mockito.when(walletRepositoryMock.findById(ID)).thenReturn(Optional.ofNullable(wallets.get(1)));


        Wallet extents = walletServiceTest.getWallet(1);
        Wallet actual = walletTwo;

        Assertions.assertNotNull(extents);
        Assertions.assertEquals(extents, actual);

    }
    @Test
    public void getWalletExceptionTest(){
        Assertions.assertThrows(WalletNotFoundException.class,
                () -> walletServiceTest.getWallet(ID));
    }
//    @Test
//    public void removeWalletTest(){
////        List<Wallet> walletsTest = new ArrayList<>(List.of(walletOne, walletTwo, walletTree));
////        Mockito.when(walletRepositoryMock.findAll()).thenReturn(walletsTest);
////        Mockito.when(walletRepositoryMock.findById(ID)).thenReturn(Optional.ofNullable(walletsTest.get(0)));
//        Mockito.when(walletRepositoryMock.delete(Wallet<walletOne>)).thenReturn(walletTwo);
//
//        walletServiceTest.removeWallet(ID);
//        verify(walletRepositoryMock).delete(walletOne);
//
//    }
    @Test
    public void removeWalletExceptionTest(){
        Assertions.assertThrows(WalletNotFoundException.class,
                () -> walletServiceTest.removeWallet(ID));
    }

    @Test
    public void getAllWalletTest(){
        Mockito.when(walletRepositoryMock.findAll()).thenReturn(wallets);
        assertArrayEquals(walletServiceTest.getAllWallet().toArray(), wallets.toArray());
    }
    @Test
    public void topUpYourAccountTest(){
        Wallet actual = new Wallet(1L, 2000L);
        Mockito.when(walletRepositoryMock.findById(ID)).thenReturn(Optional.ofNullable(wallets.get(0)));
        Mockito.when(walletRepositoryMock.save(wallets.get(0))).thenReturn(actual);
        Wallet extents = walletServiceTest.topUpYourAccount(ID, 1000);


        Assertions.assertNotNull(extents);
        Assertions.assertEquals(extents, actual);
    }
    @Test
    public void topUpYourAccountExceptionTest(){
        Assertions.assertThrows(WalletNotFoundException.class,
                () -> walletServiceTest.topUpYourAccount(ID, 1000L));
    }
    @Test
    public void withdrawTest(){
        Wallet actual = new Wallet(2L, 1000L);
        Mockito.when(walletRepositoryMock.findById(2L)).thenReturn(Optional.ofNullable(wallets.get(1)));
        Mockito.when(walletRepositoryMock.save(wallets.get(1))).thenReturn(actual);
        String extents = walletServiceTest.withdraw(2, 1000);


        Assertions.assertNotNull(extents);
        Assertions.assertEquals(extents, "Операция проведена успешно");

    }
    @Test
    public void withdrawTest2(){
        Mockito.when(walletRepositoryMock.findById(2L)).thenReturn(Optional.ofNullable(wallets.get(1)));
        String extents = walletServiceTest.withdraw(2, 3000);


        Assertions.assertNotNull(extents);
        Assertions.assertEquals(extents, "Недостаточно стредств");

    }
    @Test
    public void withdrawExceptionTest(){
        Assertions.assertThrows(WalletNotFoundException.class,
                () -> walletServiceTest.withdraw(ID, 1000L));

    }
}
