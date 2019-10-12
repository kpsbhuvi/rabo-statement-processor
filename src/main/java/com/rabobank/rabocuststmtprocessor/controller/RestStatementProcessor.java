package com.rabobank.rabocuststmtprocessor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;
import com.rabobank.rabocuststmtprocessor.service.UploadService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders="*")
public class RestStatementProcessor {
	@Autowired
	private UploadService uploadService;
	
	@PostMapping("/upload")
    public List<StmtRecord> handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

		Optional<List<StmtRecord>> fileContent = Optional.ofNullable(uploadService.store(file));
        redirectAttributes.addFlashAttribute("message",
                "You have uploaded " + file.getOriginalFilename() + " file Successfully!");

        return fileContent.get();
    }
}
