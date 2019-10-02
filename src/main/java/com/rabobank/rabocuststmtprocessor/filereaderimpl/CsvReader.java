package com.rabobank.rabocuststmtprocessor.filereaderimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;
import com.rabobank.rabocuststmtprocessor.filereader.StatementReader;

public class CsvReader implements StatementReader {

	@Override
	public List<StmtRecord> getStmtRecords(String fileContent) {
		String[] recordArray = fileContent.split("\\r?\\n");
		List<String> recordList = Arrays.asList(recordArray);
		//Ignore first row as it contains header details
		recordList = recordList.subList(1, recordList.size());
		List<StmtRecord> stmtRecordList = new ArrayList<>();
		recordList.forEach((record) -> {
			if(!record.isEmpty()){
				String[] fields = record.split(",");
				StmtRecord stmtRecord = new StmtRecord();
				stmtRecord.setTransactionRef(Integer.parseInt(fields[0]));
				stmtRecord.setAccountNum(fields[1]);
				stmtRecord.setDesc(fields[2]);
				stmtRecord.setStartBal(new BigDecimal(fields[3]));
				stmtRecord.setMutation(new BigDecimal(fields[4]));
				stmtRecord.setEndBal(new BigDecimal(fields[5]));
				
				stmtRecordList.add(stmtRecord);
			}
			
		});
		return stmtRecordList;
	}

	
}
