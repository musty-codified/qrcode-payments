package com.mustycodified.fintech_qrpayment.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustycodified.fintech_qrpayment.entity.Merchant;
import com.mustycodified.fintech_qrpayment.entity.Transaction;
import com.mustycodified.fintech_qrpayment.entity.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class CommonUtils {
    public static List<Merchant> getMerchants() {

        try {
            InputStream inputStream = new ClassPathResource("mock-data/merchants.json").getInputStream();
            return new ObjectMapper().readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> getUsers() {
        try {
            InputStream inputStream = new ClassPathResource("mock-data/users.json").getInputStream();
            return new ObjectMapper().readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Transaction> getTransactions() {
        try {
            InputStream inputStream = new ClassPathResource("mock-data/users.json").getInputStream();
            return new ObjectMapper().readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
