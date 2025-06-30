package com.aos.AOSBE.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.DTOS.InactiveUserPromotionDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.UserLogs;
import com.aos.AOSBE.Repository.AccountsRepository;
import com.aos.AOSBE.Repository.UserLogsRepository;

@Service
public class UserLogsService {
	@Autowired
	private AccountsRepository accountsRepository;
	private GenericSpecificationBuilder specBuilder;

	@Autowired
	private UserLogsRepository userLogsRepository;

	public List<UserLogs> userLogsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<UserLogs> spec = specBuilder.buildFilter(filters);
		return userLogsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public UserLogs userLogsSave(UserLogs userLogs) {
		return userLogsRepository.save(userLogs);
	}

	public Optional<UserLogs> userLogsFindById(int id) {
		return userLogsRepository.findById(id);
	}

	@Transactional
	public void userLogsDeleteById(int id) {
		userLogsRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<InactiveUserPromotionDTOS> getInactiveUsersWithSuggestion(int inactiveDays) {
		LocalDateTime cutoff = LocalDateTime.now().minusDays(inactiveDays);
		// Lấy toàn bộ ID user đã từng log
		List<Integer> activeUserIds = userLogsRepository.findDistinctUserIdAfter(cutoff);

		// Lấy toàn bộ user hiện có
		List<Accounts> allUsers = accountsRepository.findAll();

		List<InactiveUserPromotionDTOS> result = new ArrayList<>();
		for (Accounts acc : allUsers) {
			if (!activeUserIds.contains(acc.getId())) {
				result.add(new InactiveUserPromotionDTOS(acc.getId(), acc.getEmail(),
						"Không có hoạt động trong " + inactiveDays + " ngày", "GIFT-10K" // hard-code ví dụ
				));
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public List<Integer> getInactiveUserIds(int days) {
		LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
		List<Integer> activeUserIds = userLogsRepository.findDistinctUserIdAfter(cutoffDate);

		// Lấy toàn bộ user ID
		List<Integer> allUserIds = accountsRepository.findAll().stream().map(Accounts::getId).toList();

		// Lọc ra những user chưa hoạt động
		return allUserIds.stream().filter(id -> !activeUserIds.contains(id)).toList();
	}

	@Transactional(readOnly = true)
	public boolean hasUserReceivedPromotion(int userId, String couponCode) {
		return userLogsRepository.hasReceivedPromotion(userId, couponCode);
	}

}