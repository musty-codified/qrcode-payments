package com.mustycodified.fintech_qrpayment.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mustycodified.fintech_qrpayment.dto.request.PaymentProcessingRequest;
import com.mustycodified.fintech_qrpayment.dto.request.PaymentRequest;
import com.mustycodified.fintech_qrpayment.dto.response.ApiResponse;
import com.mustycodified.fintech_qrpayment.dto.response.PaymentResponse;
import com.mustycodified.fintech_qrpayment.dto.response.QRResponse;
import com.mustycodified.fintech_qrpayment.entity.Merchant;
import com.mustycodified.fintech_qrpayment.entity.Transaction;
import com.mustycodified.fintech_qrpayment.entity.User;
import com.mustycodified.fintech_qrpayment.util.AppUtil;
import com.mustycodified.fintech_qrpayment.util.CommonUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class QRPaymentController {

    private final AppUtil appUtil;

    private final List<Merchant> merchants = CommonUtils.getMerchants();
    private final List<User> users = CommonUtils.getUsers();
    private final List<Transaction> transactions = CommonUtils.getTransactions();


    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<QRResponse>> generateQRCode(@Valid @RequestBody PaymentRequest request) throws Exception {
        String qrData = request.getAmount() + "," + request.getCurrency() + "," + request.getMerchantId() + "," + request.getDescription();
        System.out.println(qrData);
        String base64String = encrypt(qrData);
        log.info("Result of encoding qrData to base64 String:{}", base64String);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(base64String, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", stream);

        String base64QRCode = Base64.getEncoder().encodeToString(stream.toByteArray());
        log.info("Result of encoding qrData to base64 Image:{}", base64QRCode);
        return ResponseEntity.ok(new ApiResponse<>(true, "Request successfully processed", new QRResponse(base64QRCode)));
    }


    @PostMapping("/process")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@Valid @RequestBody PaymentProcessingRequest request) {
        Optional<User> userOpt = users.stream()
                .filter(merchant -> merchant.getUserId().equals(request.getUserId())).findAny();
        Optional<Merchant> merchantOpt = merchants.stream()
                .filter(merchant -> merchant.getMerchantId().equals(request.getMerchantId())).findAny();
        if (userOpt.isEmpty() || merchantOpt.isEmpty()) {
            throw new RuntimeException("User or Merchant not found");
        }
        User user = userOpt.get();
        Merchant merchant = merchantOpt.get();

        synchronized (user) { // Lock user to prevent double spending
            if (user.getAvailableBalance().compareTo(request.getAmount()) < 0) {
                throw new  RuntimeException("Insufficient funds");
            }

            user.setAvailableBalance(user.getAvailableBalance().subtract(request.getAmount()));
            merchant.setAvailableBalance(merchant.getAvailableBalance().add(request.getAmount()));

            users.add(user);
            merchants.add(merchant);
            transactions.add(new Transaction(request.getUserId(), request.getMerchantId(), request.getAmount(), request.getCurrency(), "SUCCESS"));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Request successfully processed", new PaymentResponse(user.getAvailableBalance(), merchant.getAvailableBalance())));
    }

    private String encrypt(String qrData) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        char [] password = appUtil.generateRandomString(10).toCharArray();
        String salt = appUtil.generateRandomString(15);
        /* Derive the key, given password and salt. */
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt.getBytes(), 65536, 128);
        SecretKey tmp = null;
        try {
            tmp = factory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        SecretKey key = new SecretKeySpec(tmp.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return encodeToBase64Image(qrData, cipher);
    }


    private String encodeToBase64Image(String plainText, Cipher cipher) {
        try {
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (IllegalArgumentException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Invalid data", e);
        }
    }

}

