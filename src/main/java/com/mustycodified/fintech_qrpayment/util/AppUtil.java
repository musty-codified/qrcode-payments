package com.mustycodified.fintech_qrpayment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;


@Component
public class AppUtil {

    private final Random RANDOM = new SecureRandom();


    public String getFormattedNumber(final String number){
        String trimmedNumber = number.trim();
        String formattedNumber = null;
        if(trimmedNumber.startsWith("0")){
            formattedNumber = "+234" + trimmedNumber.substring(1);

        } else if(trimmedNumber.startsWith("234"))
            formattedNumber = "+" + number;

        else if (!number.startsWith("+") && Integer.parseInt(String.valueOf(number.charAt(0))) > 0){
            formattedNumber ="+234" + number;
        }
        return formattedNumber;
    }

    public void validateEmailDomain(String email) {
        if (email.indexOf("@") == email.length() - 1) {
            throw new IllegalArgumentException("Invalid email format. Email must include a domain");
        }

        if (!email.contains("@") || email.startsWith("@")) {
            throw new IllegalArgumentException("Invalid email format. Email must contain a username before the '@' symbol.");
        }

    }

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
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }

}
