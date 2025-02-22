# QR Code Generation and Payment API

A **Java-based** API with **Spring Boot**, that enables QR code-based transactions, allowing users to generate QR codes for payments and process transactions securely using 
**AES encryption**.

## 1. Prerequisites ##
- **Java 17+**
- **Spring Boot 3.4.2**
- **Maven 3.8+**

---

## 2. Running the API ##

Follow these steps to set up and run the API:

- **Clone the repository:**
  `git clone https://github.com/musty-codified/qrcode-payments.git`
- **Build and run the backend project using maven:**

  `mvn clean install`

  `mvn spring-boot:run`
- The API will start on **`http://localhost:9000`**.

---

## 3. API Documentation ##

The REST API endpoints are prefixed with `/api/v1` due to the context-path setting.

### 3.1 API Endpoints ###

- (POST) [Generate QR Code](http://localhost:9000/api/v1/payments/generate) `/api/v1/payments/generate`
- (POST) [Process Payment](http://localhost:9000/api/v1/payments/process) `/api/v1/payments/process`


---

## 4. Additional Notes

### 4.1 Testing

- Use Postman to send requests **import [qr-payment.postman-collection.json](qr-payment.postman-collection.json)**.

---











