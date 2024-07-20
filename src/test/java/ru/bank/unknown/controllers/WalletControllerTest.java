package ru.bank.unknown.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import ru.bank.unknown.contrillers.WalletController;
import ru.bank.unknown.models.Wallet;
import ru.bank.unknown.services.WalletService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {
    @Mock
    private WalletService walletServiceMock;

    @InjectMocks
    private WalletController walletControllerTest;

    private MockMvc mockMvc;
    private List<Wallet> wallets = new ArrayList<>();
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(walletControllerTest).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createWalletTest() throws Exception {
        Wallet wallet = new Wallet(1L, 1000L);
        String walletJson = objectMapper.writeValueAsString(wallet);
        mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON).content(walletJson))
                .andExpect(status().isOk());
        verify(walletServiceMock, times(1)).createWallet(wallet);
    }
    @Test
    public void getWalletTest() throws Exception {
        Wallet wallet = new Wallet(1L, 1000L);
        when(walletServiceMock.getWallet(1L)).thenReturn(wallet);
        mockMvc.perform(get("/api/v1/wallet/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(1000L));
        verify(walletServiceMock, times(1)).getWallet(1);

    }
    @Test
    public void removeWallet() throws Exception {
        Wallet wallet = new Wallet(1L, 1000L);
        String walletJson = objectMapper.writeValueAsString(wallet);
        mockMvc.perform(delete("/api/v1/wallet/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isOk());
        verify(walletServiceMock, times(1)).removeWallet(1);
    }
    @Test
    public void topUpYourAccount() throws Exception {
        Wallet wallet = new Wallet(1L, 1000L);
        String walletJson = objectMapper.writeValueAsString(wallet);
        when(walletServiceMock.topUpYourAccount(1L, 1000L)).thenReturn(wallet);
        mockMvc.perform(put("/api/v1/wallet/put_money?id=1&amount=1000", 1L, 1000L)
                .contentType(MediaType.APPLICATION_JSON).content(walletJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(1000L));
        verify(walletServiceMock, times(1)).topUpYourAccount(1L, 1000L);
    }
    @Test
    public void withdraw() throws Exception {
        Wallet wallet = new Wallet(1L, 1000L);
        String walletJson = objectMapper.writeValueAsString(wallet);
        when(walletServiceMock.withdraw(1L, 1000L)).thenReturn(String.valueOf(wallet));
        mockMvc.perform(put("/api/v1/wallet/withdraw?id=1&amount=1000", 1L, 1000L)
                        .contentType(MediaType.APPLICATION_JSON).content(walletJson))
                .andExpect(status().isOk());
        verify(walletServiceMock, times(1)).withdraw(1L, 1000L);
    }
    @Test
    public void getAllWalletTest() throws Exception {
        when(walletServiceMock.getAllWallet()).thenReturn(wallets);
        mockMvc.perform(get("/api/v1/wallet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(wallets));
        verify(walletServiceMock, times(1)).getAllWallet();
    }




}
