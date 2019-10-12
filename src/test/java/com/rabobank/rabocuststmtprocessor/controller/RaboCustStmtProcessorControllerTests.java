package com.rabobank.rabocuststmtprocessor.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.rabocuststmtprocessor.RaboCustStmtProcessorApplication;
import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;
import com.rabobank.rabocuststmtprocessor.service.UploadService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaboCustStmtProcessorApplication.class)
public class RaboCustStmtProcessorControllerTests {
	
	final static Logger logger = LoggerFactory.getLogger(RaboCustStmtProcessorControllerTests.class);
	
	MockMvc mockmvc;
	
	@Autowired
	private RestStatementProcessor rsp;
	
	@MockBean
	private UploadService us;
	
	private List<StmtRecord> recordsList = new ArrayList<StmtRecord>();
	
	
	@Before
	public void setUp() throws Exception{
		this.mockmvc = MockMvcBuilders.standaloneSetup(this.rsp).build();
		
		StmtRecord record = new StmtRecord();
		record.setAccountNum("NL93ABNA0585619023");
		record.setDesc("Tickets for Rik Theuß");
		record.setEndBal(new BigDecimal("4981"));
		record.setMutation(new BigDecimal("+1000"));
		record.setStartBal(new BigDecimal("3980"));
		record.setTransactionRef(165102);
		
		recordsList.add(record);
		
	}
	
	
	@Test
	public void getErrorRecords() throws Exception{
		when(us.store(ArgumentMatchers.any(MultipartFile.class))).thenReturn(recordsList);
		
		File file = new File("src/test/resources/testRecords.xml");
		FileInputStream fis = new FileInputStream(file);
		MockMultipartFile multipart = new MockMultipartFile("file", fis);
		MvcResult result = mockmvc.perform(MockMvcRequestBuilders.multipart("/upload").file(multipart))
				.andExpect(status().isOk()).andReturn();
		List<StmtRecord> response = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<StmtRecord>>() {
				});
		logger.info("response = "+response);
		
		assertEquals(recordsList, response);
	}
	
	
}
