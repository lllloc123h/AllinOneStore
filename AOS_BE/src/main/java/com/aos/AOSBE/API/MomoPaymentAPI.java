package com.aos.AOSBE.API;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aos.AOSBE.DTOS.EWalletTransactionsDTOS;
import com.aos.AOSBE.DTOS.MOMOPAYRequestDTO;
import com.aos.AOSBE.DTOS.MomoCallbackDTO;
import com.aos.AOSBE.DTOS.TopUpRequestDTO;
import com.aos.AOSBE.DTOS.WithdrawRequestDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.EWalletTransactions;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Mapper.EWalletTransactionsMapper;
import com.aos.AOSBE.Repository.EWalletTransactionsRepository;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EWalletTransactionsService;
import com.aos.AOSBE.Service.EWalletsService;
import com.aos.AOSBE.Service.OrdersService;

import jakarta.xml.bind.DatatypeConverter;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MomoPaymentAPI {

	@Autowired
	AccountsService accountsService;
	@Autowired
	EWalletsService eWalletsService;
	@Autowired
	OrdersService ordersService;
	@Autowired
	EWalletTransactionsService eWalletTransactionsService;
	@Autowired
	EWalletTransactionsRepository eWalletTransactionsRepository;
	@Autowired
	EWalletTransactionsMapper eWalletTransactionsMapper;

	@PostMapping("/api/e-wallet/topup")
	public ResponseEntity<?> createTopUpRequest(@RequestBody TopUpRequestDTO dto) throws Exception {
		EWalletTransactionsDTOS transaction = new EWalletTransactionsDTOS();

		String domain = System.getProperty("BE_PAKE_DOMAIN_ORIGIN");
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Accounts user = accountsService.accountsFindByEmail(userEmail).orElse(null);
		// MoMo credentials and URLs
		String endpoint = "https://payment.momo.vn/v2/gateway/api/create";

		String partnerCode = System.getProperty("MOMO_PARTNER");
		String accessKey = System.getProperty("MOMO_ACCESS_KEY");
		String secretKey = System.getProperty("MOMO_SECRECT_KEY");

		String orderId = "TOPUP" + System.currentTimeMillis();
		String requestId = UUID.randomUUID().toString();
		String returnUrl = "http://localhost:5173/momo/return";
		String notifyUrl = domain + "/api/e-wallet/callback";
		EWallets eWallets = eWalletsService.eWalletsFindByAccountEmail(userEmail).orElse(null);
		transaction.setEWallets(eWallets.getId());
		transaction.setAmount(dto.getAmount().doubleValue());
		transaction.setTransactionType("TOP_UP");
		transaction.setRelatedWalletId(user.getId());
		transaction.setOrderId(orderId);
		transaction.setStatus("PENDING");
		// Create payment data
		Map<String, String> rawData = new LinkedHashMap<>();
		rawData.put("accessKey", accessKey);
		rawData.put("amount", dto.getAmount().toString());
		rawData.put("extraData", "");
		rawData.put("ipnUrl", notifyUrl);
		rawData.put("orderId", orderId);
		rawData.put("orderInfo", "Top-up e-wallet for user ID " + dto.getUserId());
		rawData.put("partnerCode", partnerCode);
		rawData.put("redirectUrl", returnUrl);
		rawData.put("requestId", requestId);
		rawData.put("requestType", "captureWallet");

		// Signature
		String rawSignature = rawData.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
				.collect(Collectors.joining("&"));

		String signature = hmacSHA256(rawSignature, secretKey);
		rawData.put("signature", signature);

		// Save to DB
		eWalletTransactionsRepository.save(eWalletTransactionsMapper.mapperToObject(transaction));

		// Send to MoMo
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<>(rawData, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);

		String payUrl = response.getBody().get("payUrl").toString();

		return ResponseEntity.ok(Map.of("payUrl", payUrl));
	}

	@PostMapping("/api/e-wallet/orderpay")
	public ResponseEntity<?> createOrderPayRequest(@RequestBody MOMOPAYRequestDTO dto) throws Exception {

		try {
			EWalletTransactionsDTOS transaction = new EWalletTransactionsDTOS();

			String domain = System.getProperty("BE_PAKE_DOMAIN_ORIGIN");
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts user = accountsService.accountsFindByEmail(userEmail).orElse(null);
			// MoMo credentials and URLs
			String endpoint = "https://payment.momo.vn/v2/gateway/api/create";

			String partnerCode = System.getProperty("MOMO_PARTNER");
			String accessKey = System.getProperty("MOMO_ACCESS_KEY");
			String secretKey = System.getProperty("MOMO_SECRECT_KEY");

			String requestId = UUID.randomUUID().toString();
			String returnUrl = "http://localhost:5173/momo/return";
			String notifyUrl = domain + "/api/e-wallet/callback";
			Orders order = ordersService.ordersFindById(dto.getOrderId()).orElse(null);
			EWallets eWallets = eWalletsService.eWalletsFindByAccountEmail(userEmail).orElse(null);
			if (order == null
//					|| eWallets == null
			) {
				return ResponseEntity.badRequest().body(Map.of("Message", "Đã có lỗi xảy ra: Không tìm thấy Order"));
			}

			order.setPaymentStatus("PENDING");
			Orders updated = ordersService.ordersSave(order);
			transaction.setEWallets(eWallets.getId());
			transaction.setAmount(dto.getFinalToTal().doubleValue());
			transaction.setTransactionType("MOMOPAY");
			transaction.setRelatedWalletId(user.getId());
			transaction.setOrderId("" + dto.getOrderId());
			transaction.setStatus("PENDING");
			String total = dto.getFinalToTal().toString();

			total = total.substring(0, total.length() - 1);
			// Create payment data
			Map<String, String> rawData = new LinkedHashMap<>();
			rawData.put("accessKey", accessKey);
			rawData.put("amount", total);
			rawData.put("extraData", "");
			rawData.put("ipnUrl", notifyUrl);
			rawData.put("orderId", UUID.randomUUID().toString() + "_ORDER_" + dto.getOrderId() + "");
			rawData.put("orderInfo", "Thanh toán đơn hàng có mã " + dto.getOrderId());
			rawData.put("partnerCode", partnerCode);
			rawData.put("redirectUrl", returnUrl);
			rawData.put("requestId", requestId);
			rawData.put("requestType", "captureWallet");

			// Signature
			String rawSignature = rawData.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
					.collect(Collectors.joining("&"));

			String signature = hmacSHA256(rawSignature, secretKey);
			rawData.put("signature", signature);

			// Save to DB
			eWalletTransactionsRepository.save(eWalletTransactionsMapper.mapperToObject(transaction));

			// Send to MoMo
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, String>> request = new HttpEntity<>(rawData, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);
			Map<String, Object> body = response.getBody();
			if (body == null || !body.containsKey("payUrl")) {
				return ResponseEntity.badRequest()
						.body(Map.of("message", "MoMo response does not contain payUrl", "response", body));
			}
			String payUrl = response.getBody().get("payUrl").toString();

			return ResponseEntity.ok(Map.of("payUrl", payUrl));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "đã xảy ra lỗi: " + e.getMessage()));
		}

	}

	@PostMapping("/api/e-wallet/callback")
	public ResponseEntity<?> momoCallback(@RequestBody MomoCallbackDTO callback) {
		try {

			String momoOrderId = callback.getOrderId().split("_")[callback.getOrderId().split("_").length - 1];
			EWalletTransactions transaction = eWalletTransactionsService.eWalletTransactionsFindByOrderID(momoOrderId);

//			 1. Check result
			switch (callback.getResultCode()) {
			case 0: {
				if (momoOrderId.contains("TOPUP")) {
					// 2. Find transaction by MoMo orderId
					if (transaction != null && !"SUCCESS".equals(transaction.getStatus())) {
						// 3. Update transaction status
						transaction.setStatus("SUCCESS");
						eWalletTransactionsService.eWalletTransactionsSave(transaction);
						// 4. Update e-wallet balance
						EWallets userWallet = eWalletsService.eWalletsFindById(transaction.getEWallets().getId())
								.orElse(null);
						if (userWallet != null) {
							double newBalance = userWallet.getBalance() + transaction.getAmount();
							userWallet.setBalance(newBalance);
							eWalletsService.eWalletsSave(userWallet);
						}
						return ResponseEntity.ok("Transaction successful and wallet updated");
					} else {
						return ResponseEntity.ok("Transaction already processed or not found");
					}
				} else {
					if (transaction != null && !"paid".equals(transaction.getStatus())) {
						// 3. Update transaction status
						Orders order = ordersService.ordersFindById(Integer.parseInt(momoOrderId)).orElse(null);
						if (order == null) {
							return ResponseEntity.badRequest()
									.body(Map.of("Message", "Đã có lỗi xảy ra: Không tìm thấy Order"));
						}
						order.setPaymentStatus("paid");
						Orders updated = ordersService.ordersSave(order);
						transaction.setStatus("SUCCESS");
						eWalletTransactionsService.eWalletTransactionsSave(transaction);
						return ResponseEntity.ok("Transaction successful and wallet updated");
					} else {
						return ResponseEntity.ok("Transaction already processed or not found");
					}
				}
			}
			case 9000: {
				if (momoOrderId.contains("TOPUP")) {
					if (transaction != null && !"SUCCESS".equals(transaction.getStatus())) {
						transaction.setStatus("TIMEOUT");
						eWalletTransactionsService.eWalletTransactionsSave(transaction);
						return ResponseEntity.ok("Transaction successful and wallet updated");
					} else {
						return ResponseEntity.ok("Transaction already processed or not found");
					}
				} else {
					Orders order = ordersService.ordersFindById(Integer.parseInt(momoOrderId)).orElse(null);
					if (order == null) {
						return ResponseEntity.badRequest()
								.body(Map.of("Message", "Đã có lỗi xảy ra: Không tìm thấy Order"));
					}
					order.setPaymentStatus("TIMEOUT");
					Orders updated = ordersService.ordersSave(order);
					transaction.setStatus("TIMEOUT");
					eWalletTransactionsService.eWalletTransactionsSave(transaction);
					return ResponseEntity.ok("Transaction successful and wallet updated");
				}

			}
			case 1006: {
				if (momoOrderId.contains("TOPUP")) {
					if (transaction != null && !"SUCCESS".equals(transaction.getStatus())) {
						transaction.setStatus("Canceled");
						eWalletTransactionsService.eWalletTransactionsSave(transaction);
						return ResponseEntity.ok("Transaction successful and wallet updated");
					} else {
						return ResponseEntity.ok("Transaction already processed or not found");
					}
				} else {
					Orders order = ordersService.ordersFindById(Integer.parseInt(momoOrderId)).orElse(null);
					if (order == null) {
						return ResponseEntity.badRequest()
								.body(Map.of("Message", "Đã có lỗi xảy ra: Không tìm thấy Order"));
					}
					order.setPaymentStatus("Canceled");
					Orders updated = ordersService.ordersSave(order);
					transaction.setStatus("Canceled");
					eWalletTransactionsService.eWalletTransactionsSave(transaction);
					return ResponseEntity.ok("Transaction successful and wallet updated");
				}

			}
			default: {
				return ResponseEntity.ok("MoMo payment failed: " + callback.getMessage());
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Server error");
		}
	}

	@PostMapping("/api/e-wallet/withdraw/bank")
	public ResponseEntity<?> withdrawToBank(@RequestBody WithdrawRequestDTOS dto) {
		try {
			String endpoint = "https://payment.momo.vn/v2/gateway/api/disbursement/pay";

			String domain = System.getProperty("BE_PAKE_DOMAIN_ORIGIN");
			String partnerCode = System.getProperty("MOMO_PARTNER");
			String accessKey = System.getProperty("MOMO_ACCESS_KEY");
			String secretKey = System.getProperty("MOMO_SECRECT_KEY");
			String publicKey = System.getProperty("MOMO_PUBLIC_KEY");
			String returnUrl = "http://localhost:5173/momo/return";
			String notifyUrl = domain + "/api/e-wallet/callback";
			String requestId = UUID.randomUUID().toString();
			String orderId = "WD" + System.currentTimeMillis();
			String json = "{\n" + "  \"partnerCode\": \"" + partnerCode + "\",\n" + "  \"partnerRefId\": \"" + orderId
					+ "\",\n" + "  \"partnerTransId\": \"" + requestId + "\",\n" + "  \"amount\": " + dto.getAmount()
					+ ",\n" + "  \"description\": \"Withdraw \"\n" + "}";
			String encrypted = encrypt(json, publicKey);
			String rawSignature = "accessKey=" + accessKey + "&amount=" + dto.getAmount() + "&disbursementMethod="
					+ encrypted + "&extraData=" + "&orderId=" + orderId + "&orderInfo="
					+ (dto.getRequestType().equals("disburseToBank") ? "Withdraw to bank " + dto.getBankCode()
							: "Withdraw to wallet " + dto.getWalletId())
					+ "&partnerCode=" + partnerCode + "&requestId=" + requestId + "&requestType="
					+ dto.getRequestType();
			Map<String, Object> requestBody = new LinkedHashMap<>();
			requestBody.put("accessKey", accessKey);
			requestBody.put("amount", dto.getAmount());

			if (dto.getRequestType().equals("disburseToBank")) {
				requestBody.put("bankAccountHolderName", dto.getBankAccountHolderName());
				requestBody.put("bankAccountNo", dto.getBankAccount());
				requestBody.put("bankCode", dto.getBankCode());
			}

			requestBody.put("extraData", "");
			requestBody.put("ipnUrl", notifyUrl);
			requestBody.put("lang", "vi");
			requestBody.put("orderId", orderId);
			requestBody.put("orderInfo",
					dto.getRequestType().equals("disburseToBank") ? "Withdraw to bank " + dto.getBankCode()
							: "Withdraw to wallet " + dto.getWalletId());
			requestBody.put("partnerCode", partnerCode);
			requestBody.put("partnerRefId", orderId);
			requestBody.put("partnerTransId", requestId);
			requestBody.put("redirectUrl", returnUrl);
			requestBody.put("requestId", requestId);
			requestBody.put("requestType", dto.getRequestType());

			if (dto.getRequestType().equals("disburseToWallet")) {
				requestBody.put("walletId", dto.getWalletId());
			}

			requestBody.put("disbursementMethod", encrypted);

// --- Signature ---
			String signature = hmacSHA256(rawSignature, secretKey);
			requestBody.put("signature", signature);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("Message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}

	}

	public static String encrypt(String plainText, String base64PublicKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(spec);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // typical for MoMo
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public String hmacSHA256(String data, String secretKey) throws Exception {
		Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
		hmacSHA256.init(secretKeySpec);
		byte[] hash = hmacSHA256.doFinal(data.getBytes());
		return DatatypeConverter.printHexBinary(hash).toLowerCase();
	}

}
