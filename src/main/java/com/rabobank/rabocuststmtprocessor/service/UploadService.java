package com.rabobank.rabocuststmtprocessor.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;
import com.rabobank.rabocuststmtprocessor.exception.UploadException;
import com.rabobank.rabocuststmtprocessor.filereader.StatementReader;
import com.rabobank.rabocuststmtprocessor.util.ReaderIdentifier;
import com.rabobank.rabocuststmtprocessor.util.StmtValidator;

@Service
public class UploadService {
	
	final static Logger logger = LoggerFactory.getLogger(UploadService.class);

	
	@Value("${statement.upload.location}")
	private Path rootLocation;
	
	@Autowired
	private ReaderIdentifier getReader;
	
	public List<StmtRecord> store(MultipartFile file) {
		
		logger.info("rootLocation = "+rootLocation);
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		logger.info("filename = "+filename);
		try {
			if (file.isEmpty()) {
				throw new UploadException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new UploadException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
			
			// Get reader
			StatementReader reader = getReader.getReader(filename);
			
			String content = new String(Files.readAllLines(this.rootLocation.resolve(filename)).stream()
					.collect(Collectors.joining("\r\n")));
			
			logger.info("content = "+content);
			
			List<StmtRecord> recordList = reader.getStmtRecords(content);
			logger.info("recordList = "+recordList);
			return StmtValidator.validateUploadedStmt(recordList);
			
		} catch (IOException e) {
			throw new UploadException("Failed to store file " + filename, e);
		}
	}
}
