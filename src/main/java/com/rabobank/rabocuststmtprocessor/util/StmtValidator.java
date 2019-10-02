package com.rabobank.rabocuststmtprocessor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;

public final class StmtValidator {

	private StmtValidator() {
	    //No instances of this class
	}
	
	public static List<StmtRecord> validateUploadedStmt(List<StmtRecord> stmtRecordsList){
		
		List<StmtRecord> duplicateStmtRecordList = new ArrayList<>();
		List<StmtRecord> errorStmtRecordList = new ArrayList<>();
		
		/*
		 * 1. all transaction references should be unique
		 * 2. the end balance needs to be validated
		 */
		duplicateStmtRecordList = getDuplicatesMap(stmtRecordsList).values().stream()
			      .filter(duplicates -> duplicates.size() > 1)
			      .flatMap(Collection::stream)
			      .collect(Collectors.toList());
		
		System.out.println("duplicateStmtRecordList = "+duplicateStmtRecordList);
		
		errorStmtRecordList = stmtRecordsList.stream()
				.filter(record -> record.getEndBal().compareTo(record.getStartBal().add(record.getMutation())) != 0)
				.collect(Collectors.toList());
		
		System.out.println("errorStmtRecordList = "+errorStmtRecordList);
		
		//stmtRecordsList.stream().forEach(record -> System.out.println(record.getTransactionRef()+" - "+(record.getStartBal().add(record.getMutation()))));
		
		duplicateStmtRecordList.addAll(errorStmtRecordList);
		
		return duplicateStmtRecordList;
		
	}
	
	private static Map<Integer, List<StmtRecord>> getDuplicatesMap(List<StmtRecord> personList) {
		  return personList.stream().collect(Collectors.groupingBy(StmtRecord::getTransactionRef));
		}
}
