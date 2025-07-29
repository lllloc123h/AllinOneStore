package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class MessageService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
    private MessageRepository messageRepository;

    public List<Message> messageFindAll(int page, int size, Map<String, Object> filters) {
    	Pageable pageable = PageRequest.of(page, size);
		Specification<Accounts> spec = specBuilder.buildFilter(filters);
		return messageRepository.findAll(spec,pageable);
    }
    @Transactional
    public Message messageSave(Message message) {
        return messageRepository.save(message);
    }
    public Optional<Message> messageFindById(int id) {
        return messageRepository.findById(id);
    }
    @Transactional
    public void messageDeleteById(int id) {
        messageRepository.deleteById(id);
    }
}