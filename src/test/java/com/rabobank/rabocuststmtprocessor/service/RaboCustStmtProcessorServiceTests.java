package com.rabobank.rabocuststmtprocessor.service;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.rabocuststmtprocessor.RaboCustStmtProcessorApplication;
import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaboCustStmtProcessorApplication.class)
public class RaboCustStmtProcessorServiceTests {

	final static Logger logger = LoggerFactory.getLogger(RaboCustStmtProcessorServiceTests.class);
	
	@Autowired
	UploadService uploadService;
	
	@Test
	public void testXMLFailureFile() throws IOException{
		File file = new File("src/test/resources/testRecords.xml");
		FileInputStream fis = new FileInputStream(file);
		MultipartFile multipart = new MockMultipartFile("file", file.getName(), "text/plain", fis);
		
		StmtRecord record = new StmtRecord();
		record.setAccountNum("NL93ABNA0585619023");
		record.setDesc("Tickets for Rik Theuß");
		record.setEndBal(new BigDecimal("4981"));
		record.setMutation(new BigDecimal("+1000"));
		record.setStartBal(new BigDecimal("3980"));
		record.setTransactionRef(165102);
		
		List<StmtRecord> recordList = uploadService.store(multipart);
		
		assertEquals(record, recordList.get(0));		
	}
	
	@Test
	public void testXMLFileNoError() throws IOException{
		File file = new File("src/test/resources/testRecords-1.xml");
		FileInputStream fis = new FileInputStream(file);
		MultipartFile multipart = new MockMultipartFile("file", file.getName(), "text/plain", fis);
		
		/*StmtRecord record = new StmtRecord();
		record.setAccountNum("NL43AEGO0773393871");
		record.setDesc("Flowers for Jan Theuß");
		record.setEndBal(new BigDecimal("59.61"));
		record.setMutation(new BigDecimal("+43.09"));
		record.setStartBal(new BigDecimal("16.52"));
		record.setTransactionRef(170148);*/
		
		List<StmtRecord> recordList = uploadService.store(multipart);
		
		assertEquals(0, recordList.size());	
		
	}
	
	@Test
	public void testCSVFileError() throws IOException{
		File file = new File("src/test/resources/testRecords-2.csv");
		FileInputStream fis = new FileInputStream(file);
		MultipartFile multipart = new MockMultipartFile("file", file.getName(), "text/plain", fis);
		
		StmtRecord record1 = new StmtRecord();
		record1.setAccountNum("NL69ABNA0433647324");
		record1.setDesc("Clothes for Richard de Vries");
		record1.setEndBal(new BigDecimal("79.92"));
		record1.setMutation(new BigDecimal("-10.91"));
		record1.setStartBal(new BigDecimal("90.83"));
		record1.setTransactionRef(112806);
		
		StmtRecord record2 = new StmtRecord();
		record2.setAccountNum("NL93ABNA0585619023");
		record2.setDesc("Tickets from Richard Bakker");
		record2.setEndBal(new BigDecimal("147.99"));
		record2.setMutation(new BigDecimal("+45.87"));
		record2.setStartBal(new BigDecimal("102.12"));
		record2.setTransactionRef(112806);
		
		List<StmtRecord> expRecordList = new ArrayList<StmtRecord>();
		expRecordList.add(record1);
		expRecordList.add(record2);
		List<StmtRecord> recordList = uploadService.store(multipart);
		
		assertEquals(expRecordList, recordList);	
		
	}
	
	@Test
	public void testCSVErrorFile() throws IOException{
		File file = new File("src/test/resources/testRecords-3.csv");
		FileInputStream fis = new FileInputStream(file);
		MultipartFile multipart = new MockMultipartFile("file", file.getName(), "text/plain", fis);
		/*
		 * 112806,NL93ABNA0585619023,Tickets from Richard Bakker,102.12,+45.87,147.99
139524,NL43AEGO0773393871,Flowers from Jan Bakker,99.44,+41.23,140.67
		 */
		
		List<StmtRecord> recordList = uploadService.store(multipart);
		logger.info("recordList = "+recordList);
		assertEquals(0, recordList.size());
		
	}
	
	@Test
	public void testCSVFileAllError() throws IOException{
		File file = new File("src/test/resources/testRecords-4.csv");
		FileInputStream fis = new FileInputStream(file);
		MultipartFile multipart = new MockMultipartFile("file", file.getName(), "text/plain", fis);
		
		StmtRecord record1 = new StmtRecord();
		record1.setAccountNum("NL69ABNA0433647324");
		record1.setDesc("Clothes for Richard de Vries");
		record1.setEndBal(new BigDecimal("79.92"));
		record1.setMutation(new BigDecimal("-10.91"));
		record1.setStartBal(new BigDecimal("90.83"));
		record1.setTransactionRef(112806);
		
		StmtRecord record2 = new StmtRecord();
		record2.setAccountNum("NL93ABNA0585619023");
		record2.setDesc("Tickets from Richard Bakker");
		record2.setEndBal(new BigDecimal("147.99"));
		record2.setMutation(new BigDecimal("+45.87"));
		record2.setStartBal(new BigDecimal("102.12"));
		record2.setTransactionRef(112806);
		
		StmtRecord record3 = new StmtRecord();
		record3.setAccountNum("NL93ABNA0585619023");
		record3.setDesc("Tickets for Rik Theuß");
		record3.setEndBal(new BigDecimal("4981"));
		record3.setMutation(new BigDecimal("+1000"));
		record3.setStartBal(new BigDecimal("3980"));
		record3.setTransactionRef(165102);
		
		List<StmtRecord> expRecordList = new ArrayList<StmtRecord>();
		expRecordList.add(record1);
		expRecordList.add(record2);
		expRecordList.add(record3);
		List<StmtRecord> recordList = uploadService.store(multipart);
		
		assertEquals(expRecordList, recordList);	
		
	}
	
	@Test
	public void contextLoads() {
	}

}
