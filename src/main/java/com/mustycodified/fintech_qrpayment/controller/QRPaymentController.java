package com.mustycodified.fintech_qrpayment.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mustycodified.fintech_qrpayment.dto.request.PaymentRequest;
import com.mustycodified.fintech_qrpayment.dto.response.ApiResponse;
import com.mustycodified.fintech_qrpayment.dto.response.QRResponse;
import com.mustycodified.fintech_qrpayment.util.AppUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class QRPaymentController {

    private final AppUtil appUtil;

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
        return ResponseEntity.ok(new ApiResponse<>(true, "request successfully processed", new QRResponse(base64QRCode)));
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

