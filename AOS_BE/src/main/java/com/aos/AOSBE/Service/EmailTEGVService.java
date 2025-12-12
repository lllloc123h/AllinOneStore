package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aos.AOSBE.Entity.EmailTEGV;
import com.aos.AOSBE.Repository.EmailTEGVRepository;

@Service
public class EmailTEGVService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private EmailTEGVRepository EmailTEGVRepository;

	public Page<EmailTEGV> EmailTEGVFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EmailTEGV> spec = specBuilder.buildFilter(filters);
		return EmailTEGVRepository.findAll(spec, pageable);
	}

	public List<EmailTEGV> EmailTEGVFindAllNotUsingPage() {
		return EmailTEGVRepository.findAll();
	}

	public String importExcel(MultipartFile file) throws Exception {

		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);

		int count = 0; // number of rows inserted

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue; // skip header row

			EmailTEGV entity = new EmailTEGV();

			Cell nameCell = row.getCell(2);
			Cell emailCell = row.getCell(7);

			if (nameCell == null || emailCell == null || emailCell.getStringCellValue().equals(";"))
				continue;
			String rawEmail = emailCell.getStringCellValue();
			int index = rawEmail.indexOf(";");

			String handleGettingEmail = (index != -1) ? rawEmail.substring(0, index) : rawEmail;
			List<EmailTEGV> checkContain = EmailTEGVRepository.findByNameTEGV(nameCell.getStringCellValue());
			if (checkContain.size() != 0 || handleGettingEmail.equalsIgnoreCase("")
					|| handleGettingEmail.contains("yahoo") || handleGettingEmail.contains("edu")
					|| handleGettingEmail.contains("ad.homeappvietco@gmail.com")
					|| handleGettingEmail.contains("artem.s.petrov@gmail.com") || handleGettingEmail.contains("yandex")
					|| handleGettingEmail.contains("techcombank") || handleGettingEmail.contains("sales")) {
				continue;
			}
			entity.setNameTEGV(nameCell.getStringCellValue());
			entity.setEmailTEGV(handleGettingEmail);

			EmailTEGVRepository.save(entity);
			count++;
		}

		workbook.close();

		return "Imported " + count + " rows successfully.";
	}

	@Transactional
	public EmailTEGV EmailTEGVSave(EmailTEGV EmailTEGV) {
		return EmailTEGVRepository.save(EmailTEGV);
	}

	public Optional<EmailTEGV> EmailTEGVFindById(int id) {
		return EmailTEGVRepository.findById(id);
	}

	@Transactional
	public void EmailTEGVDeleteById(int id) {
		EmailTEGVRepository.deleteById(id);
	}
}