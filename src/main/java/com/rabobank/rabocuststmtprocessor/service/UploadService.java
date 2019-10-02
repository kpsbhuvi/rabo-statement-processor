package com.rabobank.rabocuststmtprocessor.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
	
	@Value("${statement.upload.location}")
	private Path rootLocation;
	
	@Autowired
	private ReaderIdentifier getReader;
	
	public List<StmtRecord> store(MultipartFile file) {
		
		System.out.println("rootLocation = "+rootLocation);
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		System.out.println("filename = "+filename);
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
			
			
			StatementReader reader = getReader.getReader(filename);
			return StmtValidator.validateUploadedStmt(reader.getStmtRecords(new String(Files.readAllBytes(this.rootLocation.resolve(filename)))));
			
		} catch (IOException e) {
			throw new UploadException("Failed to store file " + filename, e);
		}
	}
}
