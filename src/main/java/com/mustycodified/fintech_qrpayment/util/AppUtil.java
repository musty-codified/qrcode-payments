package com.mustycodified.fintech_qrpayment.util;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;


@Component
public class AppUtil {

    private final Random RANDOM = new SecureRandom();
    public  String generateSerialNumber(String prefix) {
        Random rand = new Random();
        long x = (long)(rand.nextDouble()*100000000000000L);
        return  prefix + String.format("%014d", x);
    }

    public String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i=0; i<length; i++){
            String DIGITS = "0123456789";
            returnValue.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }
        return new String(returnValue);
    }

}
