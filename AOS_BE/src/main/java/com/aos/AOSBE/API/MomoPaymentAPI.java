package com.aos.AOSBE.API;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.aos.AOSBE.DTOS.MomoCallbackDTO;
import com.aos.AOSBE.DTOS.TopUpRequestDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.EWalletTransactions;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Mapper.EWalletTransactionsMapper;
import com.aos.AOSBE.Repository.EWalletTransactionsRepository;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EWalletTransactionsService;
import com.aos.AOSBE.Service.EWalletsService;

import jakarta.xml.bind.DatatypeConverter;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MomoPaymentAPI {

	@Autowired
	AccountsService accountsService;
	@Autowired
	EWalletsService eWalletsService;
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
		String returnUrl = "http://localhost:5173/AllinOneStore/momo/return";
		String notifyUrl = domain + "/api/e-wallet/callback";

		transaction.setEWallets(user.getId());
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

	@PostMapping("/api/e-wallet/callback")
	public ResponseEntity<String> momoCallback(@RequestBody MomoCallbackDTO callback) {
		try {
//			 1. Check result
			if (callback.getResultCode() == 0) {
				String momoOrderId = callback.getOrderId();
				// 2. Find transaction by MoMo orderId
				EWalletTransactions transaction = eWalletTransactionsService
						.eWalletTransactionsFindByOrderID(momoOrderId);
				if (transaction != null && !"SUCCESS".equals(transaction.getStatus())) {
					// 3. Update transaction status
					transaction.setStatus("SUCCESS");
					eWalletTransactionsService.eWalletTransactionsSave(transaction);
					// 4. Update e-wallet balance
					EWallets userWallet = eWalletsService.eWalletsFindById(transaction.getEWallets().getId());
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
				return ResponseEntity.ok("MoMo payment failed: " + callback.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Server error");
		}
	}

	public String hmacSHA256(String data, String secretKey) throws Exception {
		Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
		hmacSHA256.init(secretKeySpec);
		byte[] hash = hmacSHA256.doFinal(data.getBytes());
		return DatatypeConverter.printHexBinary(hash).toLowerCase();
	}

}
